package com.example.musicplayer.ui.states

import androidx.media3.common.MediaItem

data class PlayerState (
    val currentMediaItem: MediaItem? = null,
    val currentSong: SongItemUiState? = null,
    val songList: List<SongItemUiState> = listOf(),
    val mediaItems: List<MediaItem> = listOf(),
    val isPlaying: Boolean = false,
    val duration: Long = 0
)