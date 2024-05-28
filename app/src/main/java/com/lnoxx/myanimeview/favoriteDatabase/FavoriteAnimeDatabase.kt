package com.lnoxx.myanimeview.favoriteDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteAnimeEntity::class], version = 1)
abstract class FavoriteAnimeDatabase: RoomDatabase() {
    abstract fun getDao():FavoriteAnimeDao
}