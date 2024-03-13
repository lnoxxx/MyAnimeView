package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class RecommendationAnimeResponse(
    val data: List<Recommendation>,
    val pagination: Pagination
)

data class Recommendation(
    val mal_id: String,
    val entry: List<AnimeRecommendation>,
    val content: String,
    val user: User
)

data class AnimeRecommendation(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val title: String
)


