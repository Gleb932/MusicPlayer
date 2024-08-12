package com.example.musicplayer.domain

import android.net.Uri
import java.util.UUID

data class Song(
    override val id: UUID,
    var title: String,
    var duration: Long? = null,
    var releaseYear: Int? = null,
    var albumId: UUID? = null,
    var genres: List<Genre> = listOf(),
    var makers: List<Maker> = listOf(),
    var sourceUri: Uri? = null,
    var coverUri: Uri? = null
): Entity