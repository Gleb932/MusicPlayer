package com.example.musicplayer.domain

data class Song(
    val title: String,
    var duration: Int? = null,
    var releaseYear: Int? = null,
    val genres: List<Genre>? = listOf(),
    var sourceUri: String? = null,
    var coverUri: String? = null,
): Entity()