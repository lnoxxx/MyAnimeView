package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.AnimeStatisticsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.CharactersResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.FullAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanAPI {
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("filter") filter: String,
        @Query("page") page: Int): TopAnimeResponse
    @GET("top/reviews")
    suspend fun getTopReviews(): ReviewsResponse
    @GET("recommendations/anime")
    suspend fun getAnimeRecommendation(): RecommendationAnimeResponse
    @GET("anime/{id}/full")
    suspend fun getFullAnimeInfo(@Path("id") id: Int): FullAnimeResponse
    @GET("anime/{id}/statistics")
    suspend fun getAnimeStatistic(@Path("id") id: Int): AnimeStatisticsResponse
    @GET("anime/{id}/reviews")
    suspend fun getAnimeComments(@Path("id") id: Int): ReviewsResponse
    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id")id : Int): CharactersResponse
    @GET("anime")
    suspend fun animeSearch(@Query("page") page: Int,
                            @Query("q") q: String,
                            @Query("type") type: String,
                            @Query("min_score") min_score: Float,
                            @Query("max_score") max_score: Float,
                            @Query("status") status: String,
                            @Query("rating") rating: String,
                            @Query("sfw") sfw: Boolean,
                            @Query("genres") genres: String,
                            @Query("order_by") order_by: String,
                            @Query("sort") sort: String,
                            ): TopAnimeResponse
}