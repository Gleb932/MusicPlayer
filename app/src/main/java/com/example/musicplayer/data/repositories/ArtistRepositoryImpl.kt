package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.mediastore.repositories.ArtistMediaStoreRepository
import com.example.musicplayer.domain.Artist
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistRepositoryImpl @Inject constructor(
    private val artistMediaStoreRepository: ArtistMediaStoreRepository
): ArtistDataRepository, BaseCompositeRepository<Artist>(listOf(artistMediaStoreRepository)) {
    override fun getLocalArtists(): StateFlow<List<Artist>> {
        return artistMediaStoreRepository.getFlow()
    }
}