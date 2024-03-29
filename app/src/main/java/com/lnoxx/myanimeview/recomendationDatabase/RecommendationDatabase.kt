package com.lnoxx.myanimeview.recomendationDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    RecommendationUpdateTime::class,
    ReviewCache::class,
    RecommendationCache::class,
], version = 1)
abstract class RecommendationDatabase: RoomDatabase(){
    abstract fun getRecommendationUpdateTimeDao(): RecommendationUpdateTimeDao
    abstract fun getReviewDao(): ReviewDao
    abstract fun getRecommendationDao(): RecommendationDao
}
