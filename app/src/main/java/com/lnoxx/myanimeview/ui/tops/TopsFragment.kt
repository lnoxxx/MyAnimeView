package com.lnoxx.myanimeview.ui.tops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentTopsBinding
import com.lnoxx.myanimeview.ui.tops.adapters.ViewPagerTopAdapter

class TopsFragment : Fragment() {
    private lateinit var binding : FragmentTopsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopsBinding.inflate(inflater)
        binding.typeListViewPager.adapter =
            ViewPagerTopAdapter(childFragmentManager, lifecycle)
        TabLayoutMediator(binding.filterTabLayout, binding.typeListViewPager){ tab, position ->
            tab.text = when(position){
                0 -> getString(R.string.airing_title)
                1 -> getString(R.string.upcoming_title)
                2 -> getString(R.string.bypopularity_title)
                3 -> getString(R.string.favorite_title)
                else -> throw IllegalArgumentException()
            }
        }.attach()
        return binding.root
    }
}