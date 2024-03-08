package com.lnoxx.myanimeview.jikanApi

import com.lnoxx.myanimeview.jikanApi.responseDataClasses.TopAnimeResponse
import retrofit2.http.GET
//import retrofit2.http.Path
import retrofit2.http.Query

interface JikanAPI {
    @GET("top/anime")
    suspend fun getTopAnime(@Query("filter") filter: String,
                            @Query("rating") rating: String): TopAnimeResponse
//    @GET("anime/{id}/full")
//    suspend fun getFullAnimeInfo(@Path("id") id: Int)
//    @GET("anime/{id}/characters")
//    suspend fun getCharactersList(@Path("id") id: Int)
}