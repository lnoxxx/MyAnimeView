package com.lnoxx.myanimeview.ui.tops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
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
        setActionBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabLayout()
        binding.typeListViewPager.offscreenPageLimit = 4
    }
    private fun setActionBar(){
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_search_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.searchIcon -> {
                        findNavController().navigate(R.id.action_navigation_tops_to_searchFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
    private fun setTabLayout(){
        binding.typeListViewPager.adapter =
            ViewPagerTopAdapter(this)
        TabLayoutMediator(binding.filterTabLayout, binding.typeListViewPager){ tab, position ->
            tab.text = when(position){
                0 -> getString(R.string.airing_title)
                1 -> getString(R.string.upcoming_title)
                2 -> getString(R.string.bypopularity_title)
                3 -> getString(R.string.favorite_title)
                else -> throw IllegalArgumentException()
            }
        }.attach()
    }
}