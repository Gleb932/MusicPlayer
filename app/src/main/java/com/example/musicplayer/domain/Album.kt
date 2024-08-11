package com.example.musicplayer.domain

import java.util.UUID


data class Album(
    override val id: UUID,
    var title: String,
    var releaseYear: Int,
    var makers: List<Maker> = listOf(),
    var coverUri: String? = null
): Entity