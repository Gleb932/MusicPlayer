package com.example.musicplayer.ui.states

import androidx.compose.ui.graphics.ImageBitmap
import androidx.media3.common.MediaItem

data class PlayerScreenUiState (
    val title: String = "No title",
    val artist: String = "Unknown artist",
    val currentMedia: MediaItem? = null,
    val bigCover: ImageBitmap? = null,
    val isPlaying: Boolean = false,
    val position: String = "",
    val progress: Float = 0F,
    val duration: String = "",
    val canSkipPrevious: Boolean = true,
    val canSkipNext: Boolean = true
)