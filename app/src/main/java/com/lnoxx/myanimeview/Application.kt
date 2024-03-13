package com.lnoxx.myanimeview

import android.app.Application
import androidx.room.Room
import com.lnoxx.myanimeview.jikanApi.enumClasses.TopFilter
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationDatabase
import com.lnoxx.myanimeview.recomendationDatabase.RecommendationUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopUpdateTime
import com.lnoxx.myanimeview.topsDatabase.TopsTypeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeViewApplication: Application() {
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
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val reviewCnt = recommendationDatabase.getRecommendationUpdateTimeDao().getCount()
            if (reviewCnt == 0){
                recommendationDatabase.getRecommendationUpdateTimeDao().insert(
                    RecommendationUpdateTime("review",0,0))
            }
            val topCnt = topsTypeDatabase.getTopUpdateTimeDao().getCount()
            if (topCnt == 0){
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.AIRING.filterToQuery(),0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.FAVORITE.filterToQuery(),0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.BYPOPULARITY.filterToQuery(),0,0)
                )
                topsTypeDatabase.getTopUpdateTimeDao().insert(
                    TopUpdateTime(
                    TopFilter.UPCOMING .filterToQuery(),0,0)
                )
            }
        }
    }
}