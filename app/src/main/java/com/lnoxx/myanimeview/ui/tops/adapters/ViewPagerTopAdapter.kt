package com.lnoxx.myanimeview.ui.tops.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lnoxx.myanimeview.jikanApi.enumClasses.TopFilter
import com.lnoxx.myanimeview.ui.tops.topListFragment.TopListFragment

class ViewPagerTopAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle){
    private val pageNumbers = 4
    override fun getItemCount() = pageNumbers
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TopListFragment.newInstance(TopFilter.AIRING.filterToQuery())
            1 -> TopListFragment.newInstance(TopFilter.UPCOMING.filterToQuery())
            2 -> TopListFragment.newInstance(TopFilter.BYPOPULARITY.filterToQuery())
            3 -> TopListFragment.newInstance(TopFilter.FAVORITE.filterToQuery())
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}