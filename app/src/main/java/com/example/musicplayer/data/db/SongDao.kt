package com.example.musicplayer.data.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SongDao{
    @Insert
    suspend fun insertSong(songEntry: SongEntry)
}