package com.lnoxx.myanimeview.topsDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    TopUpdateTime::class,
    AnimeInTop::class,
                     ], version = 1)
abstract class TopsTypeDatabase:RoomDatabase(){
    abstract fun getTopUpdateTimeDao(): TopUpdateTimeDao
    abstract fun getTopsAnimeDao(): TopsAnimeDao
}
