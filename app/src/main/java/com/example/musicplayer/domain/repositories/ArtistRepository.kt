package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.Song

interface ArtistRepository {
    fun getSongArtists(song: Song): List<Artist>
}