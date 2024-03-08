package com.lnoxx.myanimeview.topsDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TopUpdateTimeDao{
    @Insert
    fun insert(topUpdateTime: TopUpdateTime)
    @Update
    fun update(topUpdateTime: TopUpdateTime)
    @Query("SELECT * FROM topUpdateTime WHERE topType = :type")
    fun getTimeByTopType(type: String): TopUpdateTime
    @Query("SELECT COUNT(*) FROM topUpdateTime")
    fun getCount(): Int
}

@Dao
interface TopsAnimeDao{
    @Query("DELETE FROM topsAnime WHERE type = :type")
    fun deleteOldTop(type: String)
    @Insert
    fun insertAll(animeTopList: List<AnimeInTop>)
    @Query("SELECT * FROM topsAnime WHERE type = :type")
    fun getAnimeInTop(type: String): MutableList<AnimeInTop>
}