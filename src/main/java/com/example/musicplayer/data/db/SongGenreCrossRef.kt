package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["songId", "tagId"])
class SongGenreCrossRef (
    val songId: Long,
    val tagId: Long
)