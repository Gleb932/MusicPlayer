package com.example.musicplayer.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tag",
    indices = [
        Index(value = ["name"], unique = true)
    ])
data class TagEntry (
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)