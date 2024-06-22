package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "songId"])
class AlbumSongCrossRef(
    val albumId: Long,
    val songId: Long,
    val position: Int
)