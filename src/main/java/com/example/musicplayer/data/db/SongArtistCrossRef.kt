package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["songId", "artistId"])
class SongArtistCrossRef (
    val songId: Long,
    val artistId: Long,
    val roleId: Long,
    val priority: Int? = null
)