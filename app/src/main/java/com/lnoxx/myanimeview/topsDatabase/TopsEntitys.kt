package com.lnoxx.myanimeview.topsDatabase

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lnoxx.myanimeview.jikanApi.responseDataClasses.Anime

@Entity(tableName = "topUpdateTime")
data class TopUpdateTime(
    @PrimaryKey val topType: String,
    val day: Int,
    val month: Int,
)

@Entity(tableName = "topsAnime")
data class AnimeInTop(
    val malId: Int,
    val title: String,
    val episodes: Int,
    val score: Double,
    val synopsis: String,
    val type: String,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)

fun animeListToTopList(animeList: List<Anime>, type: String): MutableList<AnimeInTop>{
    val resultList = mutableListOf<AnimeInTop>()
    for ((index, anime) in animeList.withIndex()){
        if (index >= 1){
            if (anime.mal_id == animeList[index-1].mal_id) continue
        }
        resultList+=AnimeInTop(
            anime.mal_id,
            anime.title ?: "No title",
            anime.episodes ?: 0,
            anime.score ?: 0.0,
            anime.synopsis ?: "No info",
            type,
            anime.images.jpg.image_url)
    }
    return resultList
}