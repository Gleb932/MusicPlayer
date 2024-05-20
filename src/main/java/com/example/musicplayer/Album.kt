package com.example.musicplayer

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "albums")
data class Album(
    val title: String,
    val artists: List<String>,
    val release: LocalDate,
    val genres: List<String>? = null,
    val cover: ImageBitmap? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)