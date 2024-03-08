package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.enumClasses.AgesRating
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JikanMainClass {
    private val loggingInterceptor = HttpLoggingInterceptor()
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

    //user age rating PUBLIC!
    var userAgeRating = AgesRating.G

    suspend fun getAnimeTop(filter: String): TopAnimeResponse{
        return jikanAPI.getTopAnime(filter, "")
    }
}