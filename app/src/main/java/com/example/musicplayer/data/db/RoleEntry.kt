package com.example.musicplayer.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "role")
data class RoleEntry (
    val role: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)