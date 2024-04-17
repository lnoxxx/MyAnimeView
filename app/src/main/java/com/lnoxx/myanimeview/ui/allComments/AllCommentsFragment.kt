package com.lnoxx.myanimeview.ui.allComments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.databinding.FragmentAllCommentsBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.ui.animeView.adapters.viewHolders.CommentsRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCommentsFragment : Fragment() {
    lateinit var binding: FragmentAllCommentsBinding
    private var currentComments: ReviewsResponse? = null
    private var currentAnimeId = 0
    private lateinit var application: AnimeViewApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentComments = comments
        currentAnimeId = animeID
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCommentsBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.allCommentsRecyclerView.layoutManager = LinearLayoutManager(context)
        if (currentComments != null){
            binding.allCommentsRecyclerView.adapter =
                CommentsRecyclerViewAdapter(currentComments!!, false)
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                comments = application.commentsCache[currentAnimeId]
                if (comments == null){
                    comments = JikanMainClass.getAnimeComments(currentAnimeId)
                    application.commentsCache[currentAnimeId] = comments!!
                }
                withContext(Dispatchers.Main){
                    binding.allCommentsRecyclerView.adapter =
                        CommentsRecyclerViewAdapter(comments!!, false)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showBottomNavBar(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showBottomNavBar(true)
    }

    companion object{
        var comments: ReviewsResponse? = null
        var animeID = 0
    }
}