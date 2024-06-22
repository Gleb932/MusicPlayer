package com.example.musicplayer.domain.repositories

import android.graphics.Bitmap
import com.example.musicplayer.domain.Song

interface SongRepository {
    fun getLocalSongs():List<Song>
    fun getSavedSongs():List<Song>
    fun getThumbnail(song: Song, size: Pair<Int, Int>): Bitmap?
}