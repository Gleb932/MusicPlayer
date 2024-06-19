package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.Song

interface AlbumRepository {
    fun getSongAlbum(song: Song): Album?
    fun getLocalAlbums():List<Album>
    fun getSavedAlbums():List<Album>
}