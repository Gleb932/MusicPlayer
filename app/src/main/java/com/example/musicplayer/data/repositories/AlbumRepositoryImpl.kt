package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.mediastore.MediaStoreRepository
import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepositoryImpl @Inject constructor(
    private val albumMediaStoreRepository: MediaStoreRepository<Album>
): AlbumRepository, BaseCompositeRepository<Album>(listOf(albumMediaStoreRepository)) {
    override fun getLocalAlbums(): StateFlow<List<Album>> {
        return albumMediaStoreRepository.getFlow()
    }

    override fun getArtistAlbums(songId: UUID): List<Album> {
        TODO("Not yet implemented")
    }
}