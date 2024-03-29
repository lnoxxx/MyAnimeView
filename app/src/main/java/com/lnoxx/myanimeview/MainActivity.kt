package com.lnoxx.myanimeview

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lnoxx.myanimeview.databinding.ActivityMainBinding
import com.lnoxx.myanimeview.ui.favorite.FavoriteFragment
import com.lnoxx.myanimeview.ui.recommendation.RecommendationFragment
import com.lnoxx.myanimeview.ui.recommendation.adapters.RecommendationAdapter
import com.lnoxx.myanimeview.ui.tops.TopsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_tops,
                R.id.navigation_recommendation,
                R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
    fun showBottomNavBar(show: Boolean) {
        if (show) {
            binding.navView.visibility = View.VISIBLE
        } else {
            binding.navView.visibility = View.GONE
        }
    }
}