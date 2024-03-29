package com.lnoxx.myanimeview.ui.recommendation

import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.R
import com.lnoxx.myanimeview.databinding.FragmentRecommendationBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationCache
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationDatabase
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationUpdateTime
import com.lnoxx.myanimeview.recomendationDatabase.ReviewCache
import com.lnoxx.myanimeview.recomendationDatabase.responseToRecommendationCacheList
import com.lnoxx.myanimeview.recomendationDatabase.responseToReviewCacheList
import com.lnoxx.myanimeview.ui.errorAlertDialog.ErrorAlertDialog
import com.lnoxx.myanimeview.ui.recommendation.adapters.AdditionalButtonRvAdapter
import com.lnoxx.myanimeview.ui.recommendation.adapters.RecommendationAdapter
import com.lnoxx.myanimeview.ui.recommendation.adapters.ReviewRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val recommendationCount = 10
class RecommendationFragment : Fragment() {
    private lateinit var binding :FragmentRecommendationBinding
    private lateinit var recommendationDatabase: RecommendationDatabase
    private lateinit var adapter: ReviewRvAdapter
    private lateinit var bannerAdapter: RecommendationAdapter
    private lateinit var application: AnimeViewApplication

    private var dialogShowed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(inflater)
        application = requireContext().applicationContext as AnimeViewApplication
        recommendationDatabase = application.recommendationDatabase
        adapter = ReviewRvAdapter(mutableListOf())
        bannerAdapter = RecommendationAdapter(mutableListOf())
        setActionBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.reviewRecyclerView.layoutManager = GridLayoutManager(context,2)
        binding.reviewRecyclerView.adapter = adapter
        binding.bannerRecycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.bannerRecycleView.adapter = bannerAdapter
        setReviewContent()
        setRecommendationBanner()
        setAdditionalButtons()
        setSwipeToRefreshLayout()
    }

    private fun setSwipeToRefreshLayout(){
        binding.reviewSwipeRefreshLayout.isRefreshing = true
        binding.reviewSwipeRefreshLayout.setOnRefreshListener {
            binding.reviewSwipeRefreshLayout.isRefreshing = true
            CoroutineScope(Dispatchers.IO).launch {
                setReviewContentFromApi()
            }
        }
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
                        findNavController().navigate(R.id.action_navigation_recommendation_to_searchFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setAdditionalButtons(){
        binding.AdditionalRecomendRv.layoutManager = GridLayoutManager(context,2)
        binding.AdditionalRecomendRv.adapter = AdditionalButtonRvAdapter()
    }

    private fun setRecommendationBanner(){
        CoroutineScope(Dispatchers.IO).launch{
            if (bannerAdapter.itemCount == 0){
                withContext(Dispatchers.IO){
                    bannerAdapter.setAnime(RecommendationAdapter.emptyList)
                }
            }
            if (getUpdateStatus("recommendation")){
                setRecommendationFromApi()
            } else {
                setRecommendationFromDatabase()
            }
        }
    }

    private suspend fun setRecommendationFromApi(){
        try {
            val response = JikanMainClass.getAnimeRecommendation()
            val recommendationList = responseToRecommendationCacheList(response, recommendationCount)
            cacheRecommendation(recommendationList)
            withContext(Dispatchers.Main){
                bannerAdapter.setAnime(recommendationList)
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                if (!dialogShowed){
                    ErrorAlertDialog(requireContext(), e.message, getString(R.string.ok)).showDialog()
                    dialogShowed = true
                }
            }
        }
    }

    private suspend fun setRecommendationFromDatabase(){
        try {
            val recommendationList = recommendationDatabase.getRecommendationDao().getRecommendation()
            withContext(Dispatchers.Main){
                bannerAdapter.setAnime(recommendationList)
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                if (!dialogShowed){
                    ErrorAlertDialog(requireContext(), e.message, getString(R.string.ok)).showDialog()
                    dialogShowed = true
                }
            }
        }
    }

    private fun cacheRecommendation(recommendationList: MutableList<RecommendationCache>){
        val currentDay = application.currentDay
        val currentMount = application.currentMount

        val recommendationDao = recommendationDatabase.getRecommendationDao()
        val updateTimeDao = recommendationDatabase.getRecommendationUpdateTimeDao()
        recommendationDatabase.runInTransaction{
            recommendationDao.deleteOldRecommendation()
            recommendationDao.insertAll(recommendationList)
            updateTimeDao.update(RecommendationUpdateTime("recommendation", currentDay, currentMount))
        }
    }

    private fun setReviewContent(){
        CoroutineScope(Dispatchers.IO).launch {
            if (getUpdateStatus("review")){
                setReviewContentFromApi()
            } else {
                setReviewContentFromDatabase()
            }
        }
    }

    private suspend fun setReviewContentFromApi(){
        try {
            val reviewResponse = JikanMainClass.getReviewTop()
            val reviewList = responseToReviewCacheList(reviewResponse)
            cacheReviewList(reviewList)
            withContext(Dispatchers.Main){
                adapter.setReview(reviewList)
                binding.reviewSwipeRefreshLayout.isRefreshing = false
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                if (!dialogShowed){
                    ErrorAlertDialog(requireContext(), e.message, getString(R.string.ok)).showDialog()
                    dialogShowed = true
                }
                binding.reviewSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private suspend fun setReviewContentFromDatabase(){
        try {
            val reviewList = recommendationDatabase.getReviewDao().getReview()
            withContext(Dispatchers.Main){
                adapter.setReview(reviewList)
                binding.reviewSwipeRefreshLayout.isRefreshing = false
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                if (!dialogShowed){
                    ErrorAlertDialog(requireContext(), e.message, getString(R.string.ok)).showDialog()
                    dialogShowed = true
                }
                binding.reviewSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun cacheReviewList(reviewList: List<ReviewCache>){
        val currentDay = application.currentDay
        val currentMount = application.currentMount

        val reviewDao = recommendationDatabase.getReviewDao()
        val updateTimeDao = recommendationDatabase.getRecommendationUpdateTimeDao()
        recommendationDatabase.runInTransaction{
            reviewDao.deleteOldReview()
            reviewDao.insertAll(reviewList)
            updateTimeDao.update(RecommendationUpdateTime("review", currentDay, currentMount))
        }
    }

    private fun getUpdateStatus(type: String): Boolean{
        val currentDay = application.currentDay
        val currentMount = application.currentMount

        val lastUpdateTime = recommendationDatabase
            .getRecommendationUpdateTimeDao().getTimeByType(type)

        return if (currentDay > lastUpdateTime.day){
            true
        } else currentDay == lastUpdateTime.day && currentMount > lastUpdateTime.month
    }

    override fun onPause() {
        super.onPause()
        binding.reviewSwipeRefreshLayout.isRefreshing = false
    }

}