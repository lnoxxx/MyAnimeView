package com.lnoxx.myanimeview.favoriteDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteAnime")
data class FavoriteAnimeEntity(
    var malId: Int,
    var title: String,
    var image: String,
    var ep: Int,
    var score: Double,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)