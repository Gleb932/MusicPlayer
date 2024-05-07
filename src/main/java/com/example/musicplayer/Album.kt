package com.example.musicplayer

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val artists: List<String>,
    val release: LocalDate
){
    val genres: MutableList<String>? = null
    val cover: ImageBitmap? = null
}