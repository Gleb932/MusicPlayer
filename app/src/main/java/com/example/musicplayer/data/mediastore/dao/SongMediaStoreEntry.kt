package com.example.musicplayer.data.mediastore.dao

import android.net.Uri

data class SongMediaStoreEntry (
    val title: String,
    val mediaStoreUri: Uri,
    val id: Long,
    val albumId: Long,
    val artistId: Long,
    val duration: Long,
    val filepath: String,
    val year: Int
)