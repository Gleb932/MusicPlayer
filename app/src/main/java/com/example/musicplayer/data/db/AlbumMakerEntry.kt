package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "artistId"])
class AlbumMakerEntry (
    val albumId: Long,
    val artistId: Long,
    val priority: Int? = null
)