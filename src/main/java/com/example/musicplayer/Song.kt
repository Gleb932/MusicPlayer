package com.example.musicplayer

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "songs")
data class Song(
    val title: String,
    val artists: List<String>,
    val album: Album? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    var duration: Double? = null
    var release: LocalDate? = null
    var filePath: String? = null
    val genres: List<String>? = null
}