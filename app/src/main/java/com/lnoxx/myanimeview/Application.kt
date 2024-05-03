package com.lnoxx.myanimeview

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseUser
import com.lnoxx.myanimeview.jikanApi.enumClasses.TopFilter
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.AnimeStatisticData
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.CharactersResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationDatabase
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopsTypeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class AnimeViewApplication: Application() {
    val user: FirebaseUser? = null
    //кэш
    val commentsCache = mutableMapOf<Int, ReviewsResponse>()
    val animeCache = mutableMapOf<Int, Anime>()
    val statisticCache = mutableMapOf<Int, AnimeStatisticData>()
    val charactersCache = mutableMapOf<Int, CharactersResponse>()
    //текущее время
    private val calendar = Calendar.getInstance()
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMount = calendar.get(Calendar.MONTH)
    //бд
    val topsTypeDatabase by lazy {
        Room.databaseBuilder(
            this,
            TopsTypeDatabase::class.java,
            "TopTypesDatabase"
        ).build()
    }
    val recommendationDatabase by lazy {
        Room.databaseBuilder(
            this,
            RecommendationDatabase::class.java,
            "RecommendationDatabase"
        ).build()
    }
    // иницилизация времени в бд
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val reviewCnt = recommendationDatabase.getRecommendationUpdateTimeDao().getCount()
            if (reviewCnt == 0){
                recommendationDatabase.getRecommendationUpdateTimeDao().insert(
                    RecommendationUpdateTime("review",0,0))
                recommendationDatabase.getRecommendationUpdateTimeDao().insert(
                    RecommendationUpdateTime("recommendation",0,0))
            }
            val topCnt = topsTypeDatabase.getTopUpdateTimeDao().getCount()
            if (topCnt == 0){
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.AIRING.query,0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.FAVORITE.query,0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.BYPOPULARITY.query,0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.UPCOMING.query,0,0)
                )
            }
        }
    }
}