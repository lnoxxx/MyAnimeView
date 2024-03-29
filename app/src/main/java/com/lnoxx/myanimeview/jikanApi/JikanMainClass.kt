package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Semaphore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val requestPerSecond = 2
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

    suspend fun getAnimeTop(filter: String, page: Int = 1): TopAnimeResponse{
        try {
            semaphore.acquire()
            val response = jikanAPI.getTopAnime(filter, page)
            delay(serverDelay)
            semaphore.release()
            return response
        }catch (e: Exception){
            semaphore.release()
            throw e
        }
    }
    suspend fun getReviewTop(): ReviewsResponse{
        try{
            semaphore.acquire()
            val response = jikanAPI.getTopReviews()
            delay(serverDelay)
            semaphore.release()
            return response
        }catch (e: Exception){
            semaphore.release()
            throw e
        }
    }
    suspend fun getAnimeRecommendation(): RecommendationAnimeResponse{
        try{
            semaphore.acquire()
            val response = jikanAPI.getAnimeRecommendation()
            delay(serverDelay)
            semaphore.release()
            return response
        }catch (e: Exception){
            semaphore.release()
            throw e
        }
    }

    suspend fun getFullAnimeInfo(malId: Int): Anime{
        try{
            semaphore.acquire()
            val response = jikanAPI.getFullAnimeInfo(malId)
            delay(serverDelay)
            semaphore.release()
            return response
        }catch (e: Exception){
            semaphore.release()
            throw e
        }
    }
}