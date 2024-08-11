package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Song
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface SongRepository: Repository<Song> {
    fun getLocalSongs(): StateFlow<List<Song>>
    fun getAlbumSongs(albumId: UUID): List<Song>
}