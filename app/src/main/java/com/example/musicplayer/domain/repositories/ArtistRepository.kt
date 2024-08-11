package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Artist
import kotlinx.coroutines.flow.StateFlow

interface ArtistRepository: Repository<Artist> {
    fun getLocalArtists(): StateFlow<List<Artist>>
}