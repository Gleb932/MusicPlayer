package com.example.musicplayer.ui.states

import androidx.media3.common.MediaItem

data class PlayerBarUiState(
    val mediaItem: MediaItem? = null,
    val isPlaying: Boolean = false
)