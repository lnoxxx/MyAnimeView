package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import retrofit2.http.GET
//import retrofit2.http.Path
import retrofit2.http.Query

interface JikanAPI {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("filter") filter: String): TopAnimeResponse
    @GET("top/reviews")
    suspend fun getTopReviews(@Query("spoilers") spoilers: Boolean): ReviewsResponse
    @GET("recommendations/anime")
    suspend fun getAnimeRecommendation(): RecommendationAnimeResponse
}