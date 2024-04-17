package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class ReviewsResponse(
    val data: MutableList<Review>,
    val pagination: Pagination
)

data class Review(
    val user: User,
    val entry: EntryReview,
    val mal_id: Int,
    val url: String,
    val type: String,
    val reactions: Reactions,
    val date: String,
    val review: String,
    val score: Int,
    val tags: List<String>,
    val is_spoiler: Boolean,
    val is_preliminary: Boolean,
    val episodes_watched: Int
)

data class User(
    val username: String,
    val url: String,
    val images: Images
)

data class EntryReview(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val title: String
)

data class Reactions(
    val overall: Int,
    val nice: Int,
    val love_it: Int,
    val funny: Int,
    val confusing: Int,
    val informative: Int,
    val well_written: Int,
    val creative: Int
)

data class Pagination(
    val last_visible_page: Int,
    val has_next_page: Boolean
)

