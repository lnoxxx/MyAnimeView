package com.lnoxx.myanimeview.recomendationDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse

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

fun responseToReviewCacheList(response: ReviewsResponse): MutableList<ReviewCache>{
    val reviewList = mutableListOf<ReviewCache>()
    response.data.forEach {review ->
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