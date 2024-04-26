package com.lnoxx.myanimeview.ui.tops.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lnoxx.myanimeview.jikanApi.enumClasses.TopFilter
import com.lnoxx.myanimeview.ui.tops.topListFragment.TopListFragment

class ViewPagerTopAdapter(fragment: Fragment)
    : FragmentStateAdapter(fragment){
    private val pageNumbers = 4
    override fun getItemCount() = pageNumbers
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TopListFragment.newInstance(TopFilter.AIRING.query)
            1 -> TopListFragment.newInstance(TopFilter.UPCOMING.query)
            2 -> TopListFragment.newInstance(TopFilter.BYPOPULARITY.query)
            3 -> TopListFragment.newInstance(TopFilter.FAVORITE.query)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}