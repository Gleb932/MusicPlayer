package com.example.musicplayer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist")
class PlaylistEntry (
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)