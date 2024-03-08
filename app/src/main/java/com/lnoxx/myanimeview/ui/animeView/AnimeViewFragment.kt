package com.lnoxx.myanimeview.ui.animeView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lnoxx.myanimeview.databinding.FragmentAnimeViewBinding

class AnimeViewFragment : Fragment() {
    private lateinit var binding: FragmentAnimeViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeViewBinding.inflate(inflater)
        return binding.root
    }
}