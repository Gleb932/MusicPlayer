package com.example.musicplayer.ui.states

import androidx.compose.ui.graphics.ImageBitmap
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.Song

data class SongItemUiState (
    val song: Song,
    val artists: List<Artist> = listOf(),
    val cover: ImageBitmap? = null,
    var bigCover: ImageBitmap? = null
)