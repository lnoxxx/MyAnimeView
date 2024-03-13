package com.lnoxx.myanimeview.ui.recommendation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnoxx.myanimeview.AnimeViewApplication
import com.lnoxx.myanimeview.databinding.FragmentRecommendationBinding
import com.lnoxx.myanimeview.jikanApi.JikanMainClass
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.AnimeRecommendation
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationDatabase
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationUpdateTime
import com.lnoxx.myanimeview.recomendationDatabase.ReviewCache
import com.lnoxx.myanimeview.recomendationDatabase.responseToReviewCacheList
import com.lnoxx.myanimeview.ui.recommendation.adapters.RecommendationAdapter
import com.lnoxx.myanimeview.ui.recommendation.adapters.ReviewRvAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

const val recommendationCount = 10
class RecommendationFragment : Fragment() {
    private lateinit var binding :FragmentRecommendationBinding
    private lateinit var recommendationDatabase: RecommendationDatabase
    private lateinit var adapter: ReviewRvAdapter
    private lateinit var bannerAdapter: RecommendationAdapter

    private var reviewSpoilers = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendationBinding.inflate(inflater)

        val application = requireContext().applicationContext as AnimeViewApplication
        recommendationDatabase = application.recommendationDatabase

        adapter = ReviewRvAdapter(mutableListOf())
        bannerAdapter = RecommendationAdapter(mutableListOf())

        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.reviewRecyclerView.adapter = adapter

        binding.bannerViewPager.adapter = bannerAdapter

        setReviewContent()
        setRecommendationBanner()

        return binding.root
    }

    private fun setRecommendationBanner(){
        CoroutineScope(Dispatchers.IO).launch{
            val response = JikanMainClass.getAnimeRecommendation()
            val animeList = mutableListOf<AnimeRecommendation>()
            repeat(recommendationCount){
                if (it < response.data.size){
                    if (response.data[it].entry.isNotEmpty()){
                        animeList.add(response.data[it].entry[0])
                    }
                }
            }
            withContext(Dispatchers.Main){
                bannerAdapter.setAnime(animeList)
            }
        }
    }

    private fun setReviewContent(){
        CoroutineScope(Dispatchers.IO).launch {
            if (getReviewUpdateStatus()){
                setReviewContentFromApi()
            } else {
                setReviewContentFromDatabase()
            }
        }
    }

    private suspend fun setReviewContentFromApi(){
        try {
            val reviewResponse = JikanMainClass.getReviewTop(reviewSpoilers)
            val reviewList = responseToReviewCacheList(reviewResponse)
            cacheReviewList(reviewList)
            withContext(Dispatchers.Main){
                adapter.setReview(reviewList)
            }
        }catch (e: Exception){
            Log.d("mylog", e.message.toString())
        }
    }

    private suspend fun setReviewContentFromDatabase(){
        try {
            val reviewList = recommendationDatabase.getReviewDao().getReview()
            withContext(Dispatchers.Main){
                adapter.setReview(reviewList)
            }
        }catch (e: Exception){
            Log.d("mylog", e.message.toString())
        }
    }

    private fun cacheReviewList(reviewList: List<ReviewCache>){
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMount = calendar.get(Calendar.MONTH)

        val reviewDao = recommendationDatabase.getReviewDao()
        val updateTimeDao = recommendationDatabase.getRecommendationUpdateTimeDao()
        recommendationDatabase.runInTransaction{
            reviewDao.deleteOldReview()
            reviewDao.insertAll(reviewList)
            updateTimeDao.update(RecommendationUpdateTime("review", currentDay, currentMount))
        }
    }

    private fun getReviewUpdateStatus(): Boolean{
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMount = calendar.get(Calendar.MONTH)

        val lastUpdateTime = recommendationDatabase
            .getRecommendationUpdateTimeDao().getTimeByType("review")

        return if (currentDay > lastUpdateTime.day){
            true
        } else currentDay == lastUpdateTime.day && currentMount > lastUpdateTime.month
    }
}