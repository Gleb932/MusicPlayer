package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Album
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface AlbumRepository: Repository<Album> {
    fun getLocalAlbums(): StateFlow<List<Album>>
    fun getArtistAlbums(songId: UUID): List<Album>
}