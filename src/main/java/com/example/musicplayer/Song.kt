package com.example.musicplayer

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var title: String,
    val artists: MutableList<String>,
    var album: Album? = null
) {
    var duration: Double? = null
    var release: LocalDate? = null
    var filePath: String? = null
    val genres: MutableList<String>? = null
}