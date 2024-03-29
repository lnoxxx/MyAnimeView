package com.lnoxx.myanimeview.ui.animeView

import android.os.Bundle
import android.os.LimitExceededException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.MainActivity
import com.lnoxx.myanimeview.databinding.FragmentAnimeViewBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.ui.animeView.adapters.AnimeViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewFragment : Fragment() {
    private lateinit var binding: FragmentAnimeViewBinding
    private var animeId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeId = AnimeViewFragment.animeId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeViewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.AnimeViewRecyclerView.layoutManager = LinearLayoutManager(context)
        CoroutineScope(Dispatchers.IO).launch {
            val animeInfo = JikanMainClass.getFullAnimeInfo(animeId)
            launch(Dispatchers.Main){
                binding.AnimeViewRecyclerView.adapter = AnimeViewAdapter(animeInfo)
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