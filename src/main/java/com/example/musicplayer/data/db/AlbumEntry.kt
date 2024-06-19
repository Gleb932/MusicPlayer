package com.example.musicplayer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntry(
    val title: String,
    val releaseYear: Int? = null,
    val coverPath: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)