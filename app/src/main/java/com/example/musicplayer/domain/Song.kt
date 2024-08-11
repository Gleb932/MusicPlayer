package com.example.musicplayer.domain

import androidx.compose.ui.graphics.ImageBitmap
import java.util.UUID

data class Song(
    override val id: UUID,
    var title: String,
    var duration: Long? = null,
    var releaseYear: Int? = null,
    var albumId: UUID? = null,
    var genres: List<Genre> = listOf(),
    var makers: List<Maker> = listOf(),
    var sourceUri: String? = null,
    var coverUri: String? = null,
    var cover: ImageBitmap? = null
): Entity