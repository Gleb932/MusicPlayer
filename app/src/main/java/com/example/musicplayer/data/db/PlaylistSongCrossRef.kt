package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["playlistId", "songId"])
class PlaylistSongCrossRef(
    val playlistId: Long,
    val songId: Long,
    val position: Int
)