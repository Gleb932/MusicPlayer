package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.mediastore.MediaStoreRepository
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.repositories.ArtistRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistRepositoryImpl @Inject constructor(
    private val artistMediaStoreRepository: MediaStoreRepository<Artist>
): ArtistRepository, BaseCompositeRepository<Artist>(listOf(artistMediaStoreRepository)) {
    override fun getLocalArtists(): StateFlow<List<Artist>> {
        return artistMediaStoreRepository.getFlow()
    }
}