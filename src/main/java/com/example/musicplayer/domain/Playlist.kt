package com.example.musicplayer.domain

import java.time.LocalDateTime

data class Playlist(
    val name: String,
    val created: LocalDateTime,
    val songs: List<Song> = listOf()
): Entity()