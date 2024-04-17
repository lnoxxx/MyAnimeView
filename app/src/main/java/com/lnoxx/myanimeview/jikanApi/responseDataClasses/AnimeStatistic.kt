package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class AnimeStatisticsResponse(
    val data: AnimeStatisticData
)

data class AnimeStatisticData(
    val watching: Int,
    val completed: Int,
    val on_hold: Int,
    val dropped: Int,
    val plan_to_watch: Int,
    val total: Int,
    val scores: List<StatisticScore>
)

data class StatisticScore(
    val score: Int,
    val votes: Int,
    val percentage: Float
)
