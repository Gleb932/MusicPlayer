package com.example.musicplayer.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.RESTRICT
import androidx.room.PrimaryKey

@Entity(tableName = "song",
    foreignKeys = [
        ForeignKey(
            entity = AlbumEntry::class,
            parentColumns = ["id"],
            childColumns = ["albumId"],
            onDelete = RESTRICT
        )
    ]
)
data class SongEntry(
    val title: String,
    val duration: Int? = null,
    val releaseYear: Int? = null,
    val sourceUri: String? = null,
    val coverPath: String? = null,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val albumId: Long? = null,
)