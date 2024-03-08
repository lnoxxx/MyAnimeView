package com.lnoxx.myanimeview.ui.recommendation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lnoxx.myanimeview.databinding.FragmentRecommendationBinding

class RecommendationFragment : Fragment() {
    private lateinit var binding :FragmentRecommendationBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(inflater)
        return binding.root
    }
}