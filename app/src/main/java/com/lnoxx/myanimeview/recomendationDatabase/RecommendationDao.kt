package com.lnoxx.myanimeview.recomendationDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecommendationUpdateTimeDao{
    @Insert
    fun insert(recommendationUpdateTime: RecommendationUpdateTime)
    @Update
    fun update(recommendationUpdateTime: RecommendationUpdateTime)
    @Query("SELECT * FROM recommendationUpdateTime WHERE type = :type")
    fun getTimeByType(type: String): RecommendationUpdateTime
    @Query("SELECT COUNT(*) FROM recommendationUpdateTime")
    fun getCount(): Int
}

@Dao
interface ReviewDao{
    @Query("DELETE FROM review")
    fun deleteOldReview()
    @Insert
    fun insertAll(reviewList : List<ReviewCache>)
    @Query("SELECT * FROM review")
    fun getReview(): MutableList<ReviewCache>
}