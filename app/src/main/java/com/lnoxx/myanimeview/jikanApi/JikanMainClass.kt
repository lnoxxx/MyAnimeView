package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Semaphore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val requestPerSecond = 3
const val serverDelay = 1000L
object JikanMainClass {
    private val loggingInterceptor = HttpLoggingInterceptor()
    private val semaphore = Semaphore(requestPerSecond)
    init {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v4/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val jikanAPI = retrofit.create(JikanAPI::class.java)

    suspend fun getAnimeTop(filter: String): TopAnimeResponse{
        semaphore.acquire()
        val response = jikanAPI.getTopAnime(filter)
        delay(serverDelay)
        semaphore.release()
        return response
    }
    suspend fun getReviewTop(spoilers: Boolean): ReviewsResponse{
        semaphore.acquire()
        val response = jikanAPI.getTopReviews(spoilers)
        delay(serverDelay)
        semaphore.release()
        return response
    }
    suspend fun getAnimeRecommendation(): RecommendationAnimeResponse{
        semaphore.acquire()
        val response = jikanAPI.getAnimeRecommendation()
        delay(serverDelay)
        semaphore.release()
        return response
    }
}