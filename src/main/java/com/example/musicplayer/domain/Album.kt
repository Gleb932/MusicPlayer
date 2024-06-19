package com.example.musicplayer.domain


data class Album(
    val title: String,
    val releaseYear: Int,
    val songs: List<Song> = listOf(),
    val makers: List<Maker> = listOf(),
    val coverUri: String? = null
): Entity()