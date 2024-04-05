package com.lnoxx.myanimeview.ui.animeView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.databinding.FragmentAnimeViewBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewFragment : Fragment() {
    private lateinit var binding: FragmentAnimeViewBinding
    private var animeId = 0
    private lateinit var application: AnimeViewApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeId = AnimeViewFragment.animeId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeViewBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        binding.AnimeViewRecyclerView.alpha = 0f
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AnimeViewRecyclerView.layoutManager = LinearLayoutManager(context)
        setContent()
    }

    private fun setContent(){
        CoroutineScope(Dispatchers.IO).launch {
            val animeInCache = application.animeSessionCache[animeId]
            val animeInfo: Anime = if (animeInCache != null){
                animeInCache
            } else {
                val response = JikanMainClass.getFullAnimeInfo(animeId)
                application.animeSessionCache[response.mal_id] = response
                response
            }
            launch(Dispatchers.Main){
                val actionBar = (activity as? AppCompatActivity)?.supportActionBar
                if (actionBar != null) actionBar.title = animeInfo.title
                binding.AnimeViewRecyclerView.adapter =
                    AnimeViewAdapter(animeInfo)
                binding.AnimeViewRecyclerView.animate().apply {
                    duration = 200
                    alpha(1f)
                }
                binding.progressIndicator.hide()
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
        var animeId = 0
    }
}