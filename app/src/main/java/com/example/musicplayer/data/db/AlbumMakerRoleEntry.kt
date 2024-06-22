package com.example.musicplayer.data.db

import androidx.room.Entity

@Entity(primaryKeys = ["albumId", "artistId", "roleId"])
class AlbumMakerRoleEntry (
    val albumId: Long,
    val artistId: Long,
    val roleId: Long
)