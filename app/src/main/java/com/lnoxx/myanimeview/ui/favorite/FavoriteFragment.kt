package com.lnoxx.myanimeview.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lnoxx.myanimeview.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }
}