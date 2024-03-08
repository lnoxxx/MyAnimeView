package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class Anime(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val title: String,
    val type: String,
    val source: String,
    val episodes: Int,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val score: Double,
    val scored_by: Int,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String,
    val background: String,
    val season: String,
    val year: Int,
    val broadcast: Broadcast,
    val producers: List<Producer>,
    val licensors: List<Any>, // You can define a proper data class if needed
    val studios: List<Studio>,
    val genres: List<Genre>,
    val explicit_genres: List<Any>, // You can define a proper data class if needed
    val themes: List<Any>, // You can define a proper data class if needed
    val demographics: List<Demographic>,
    val relations: List<Relation>,
    val theme: Theme,
    val external: List<External>,
    val streaming: List<Streaming>
)

data class Images(
    val jpg: Image,
    val webp: Image
)

data class Image(
    val image_url: String,
    val small_image_url: String,
    val large_image_url: String
)

data class Trailer(
    val youtube_id: String,
    val url: String,
    val embed_url: String,
    val images: TrailerImages
)

data class TrailerImages(
    val image_url: String,
    val small_image_url: String,
    val medium_image_url: String,
    val large_image_url: String,
    val maximum_image_url: String
)

data class Aired(
    val from: String,
    val to: String?,
    val prop: AiredProp,
    val string: String
)

data class AiredProp(
    val from: AiredDate?,
    val to: AiredDate?
)

data class AiredDate(
    val day: Int?,
    val month: Int?,
    val year: Int?
)

data class Broadcast(
    val day: String,
    val time: String,
    val timezone: String,
    val string: String
)

data class Producer(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class Studio(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class Genre(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class Demographic(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class Relation(
    val relation: String,
    val entry: List<Entry>
)

data class Entry(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

data class Theme(
    val openings: List<String>,
    val endings: List<String>
)

data class External(
    val name: String,
    val url: String
)

data class Streaming(
    val name: String,
    val url: String
)