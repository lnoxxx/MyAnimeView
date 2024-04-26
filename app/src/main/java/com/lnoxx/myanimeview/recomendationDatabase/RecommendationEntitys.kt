package com.lnoxx.myanimeview.recomendationDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Images
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.ui.recommendation.recommendationCount

@Entity(tableName = "recommendationUpdateTime")
data class RecommendationUpdateTime(
    @PrimaryKey val type: String,
    val day: Int,
    val month: Int,
)

@Entity(tableName = "review")
data class ReviewCache(
    val malId: Int,
    val userImage: String,
    val userName: String,
    val animeImage: String,
    val animeName: String,
    val review: String,
    val score: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

@Entity(tableName = "recommendation")
data class RecommendationCache(
    val mal_id: Int,
    val url: String,
    val images: String,
    val title: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun responseToRecommendationCacheList(response: RecommendationAnimeResponse, count: Int):
        MutableList<RecommendationCache>{
    val resultList = mutableListOf<RecommendationCache>()
    repeat(recommendationCount){
        if (it < response.data.size){
            if (response.data[it].entry.isNotEmpty()){
                val current = response.data[it].entry[0]
                resultList.add(
                    RecommendationCache(
                        current.mal_id,
                        current.url,
                        current.images.jpg.large_image_url ?: current.images.jpg.image_url,
                        current.title)
                )
            }
        }
    }
    return resultList
}

fun responseToReviewCacheList(response: ReviewsResponse): MutableList<ReviewCache>{
    val reviewList = mutableListOf<ReviewCache>()
    response.data?.forEach { review ->
        reviewList.add(
            ReviewCache(
                review.mal_id,
                review.user.images.jpg.image_url ?: "?",
                review.user.username ?: "?",
                review.entry.images.jpg.large_image_url ?: review.entry.images.jpg.image_url ?: "?",
                review.entry.title ?: "?",
                review.review ?: "?",
                review.score)
        )
    }
    return reviewList
}