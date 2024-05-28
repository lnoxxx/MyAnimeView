package com.lnoxx.myanimeview.favoriteDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteAnimeDao{
    @Insert
    fun cache(anime: FavoriteAnimeEntity)

    @Query("SELECT * FROM favoriteAnime WHERE malId = :malId")
    fun getByMalId(malId: Int): FavoriteAnimeEntity?
}