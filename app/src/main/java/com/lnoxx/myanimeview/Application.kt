package com.lnoxx.myanimeview

import android.app.Application
import androidx.room.Room
import com.lnoxx.myanimeview.jikanApi.enumClasses.TopFilter
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

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            val cnt = topsTypeDatabase.getTopUpdateTimeDao().getCount()
            if (cnt == 0){
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