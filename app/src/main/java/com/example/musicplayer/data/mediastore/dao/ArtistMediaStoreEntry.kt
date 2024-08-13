package com.example.musicplayer.data.mediastore.dao

import android.net.Uri

data class ArtistMediaStoreEntry (
    val name: String,
    val mediaStoreUri: Uri,
    val id: Long
)