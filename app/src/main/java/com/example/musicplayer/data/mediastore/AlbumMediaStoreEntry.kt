package com.example.musicplayer.data.mediastore

import android.net.Uri

data class AlbumMediaStoreEntry (
    val name: String,
    val mediaStoreUri: Uri,
    val id: Long,
    val firstYear: Int,
    val lastYear: Int
)