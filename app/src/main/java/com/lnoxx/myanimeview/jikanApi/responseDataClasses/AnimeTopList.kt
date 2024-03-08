package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class TopAnimeResponse(
    val data: List<Anime>,
    val pagination: TopPagination
)

data class TopPagination(
    val last_visible_page: Int,
    val has_next_page: Boolean,
    val items: Items
)

data class Items(
    val count: Int,
    val total: Int,
    val per_page: Int
)