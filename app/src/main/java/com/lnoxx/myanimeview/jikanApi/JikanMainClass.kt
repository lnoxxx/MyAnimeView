package com.lnoxx.myanimeview.jikanApi

import android.util.Log
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.AnimeStatisticData
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.CharactersResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.RecommendationAnimeResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.ReviewsResponse
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import com.lnoxx.myanimeview.ui.search.SearchFilter
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Semaphore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val requestPerSecond = 1
const val serverDelay = 500L
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

    suspend fun getAnimeTop(filter: String, page: Int = 1): TopAnimeResponse?{
        return try {
            semaphore.acquire()
            val response = jikanAPI.getTopAnime(filter, page)
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }
    suspend fun getReviewTop(): ReviewsResponse?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getTopReviews()
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }
    suspend fun getAnimeRecommendation(): RecommendationAnimeResponse?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getAnimeRecommendation()
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }

    suspend fun getFullAnimeInfo(malId: Int): Anime?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getFullAnimeInfo(malId).data
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }

    suspend fun getBaseAnimeInfo(malId: Int): Anime?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getBaseAnimeId(malId).data
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }

    suspend fun getAnimeStatistic(malId: Int): AnimeStatisticData?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getAnimeStatistic(malId).data
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }

    suspend fun getAnimeComments(malId: Int): ReviewsResponse?{
        return try{
            semaphore.acquire()
            val response = jikanAPI.getAnimeComments(malId)
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }
    suspend fun getAnimeCharacters(malId: Int): CharactersResponse?{
        return try{
            semaphore.acquire()
            val response =  jikanAPI.getAnimeCharacters(malId)
            delay(serverDelay)
            semaphore.release()
            response
        }catch (e: Exception){
            semaphore.release()
            null
        }
    }

    suspend fun animeSearch(searchFilter: SearchFilter, page: Int): TopAnimeResponse?{
        try {
            semaphore.acquire()
            var genres = ""
            searchFilter.genres.forEach {
                genres+="${ it.malId},"
            }
            if (genres.isNotEmpty()){
                genres = genres.substring(0, genres.length-1)
            }
            val result = jikanAPI.animeSearch(page,
                searchFilter.query ?: "",
                searchFilter.type?.query ?: "",
                searchFilter.minScore,
                searchFilter.maxFloat,
                searchFilter.status?.query ?: "",
                searchFilter.rating?.query ?: "",
                searchFilter.sfw,
                genres,
                searchFilter.sortFilter.query,
                searchFilter.sortType.query,)
            semaphore.release()
            return result
        }catch (e: Exception){
            semaphore.release()
            return null
        }
    }
}