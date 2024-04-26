package com.lnoxx.myanimeview.jikanApi.responseDataClasses

data class CharactersResponse(
    val data: List<CharacterData>
)

data class CharacterData(
    val character: Character,
    val role: String?,
    val voice_actors: List<VoiceActor>
)

data class Character(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val name: String?
)

data class VoiceActor(
    val person: Person,
    val language: String
)

data class Person(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val name: String
)
