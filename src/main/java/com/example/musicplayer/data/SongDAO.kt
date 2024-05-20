package com.example.musicplayer.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.musicplayer.Song

@Dao
interface SongDAO{
    @Insert
    suspend fun insertSong(song: Song)
}