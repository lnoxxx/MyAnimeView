package com.lnoxx.myanimeview.jikanApi.enumClasses

enum class TopFilter {
    AIRING,
    UPCOMING,
    BYPOPULARITY,
    FAVORITE,
    ;
    fun filterToQuery() = when(this){
        AIRING -> "airing"
        UPCOMING -> "upcoming"
        BYPOPULARITY -> "bypopularity"
        FAVORITE -> "favorite"
    }
}