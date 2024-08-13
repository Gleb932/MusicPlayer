package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.mediastore.MediaStoreRepository
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.SongRepository
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepositoryImpl @Inject constructor(
    private val songMediaStoreRepository: MediaStoreRepository<Song>
): SongRepository, BaseCompositeRepository<Song>(listOf(songMediaStoreRepository)) {
    override fun getAlbumSongs(albumId: UUID): List<Song> {
        return getAll().filter { it.albumId == albumId }
    }

    override fun getLocalSongs(): StateFlow<List<Song>> {
        return songMediaStoreRepository.getFlow()
    }
}