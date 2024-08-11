package com.example.musicplayer.ui.states

import androidx.media3.common.MediaItem
import com.example.musicplayer.domain.Song

data class PlayerState (
    val currentMediaItem: MediaItem? = null,
    val songList: List<Song> = listOf(),
    val mediaItems: List<MediaItem> = listOf(),
    val isPlaying: Boolean = false,
    val duration: Long = 0
)