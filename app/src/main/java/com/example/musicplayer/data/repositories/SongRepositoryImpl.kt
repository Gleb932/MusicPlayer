package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.mediastore.repositories.SongMediaStoreRepository
import com.example.musicplayer.domain.Song
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongRepositoryImpl @Inject constructor(
    private val songMediaStoreRepository: SongMediaStoreRepository
): SongDataRepository, BaseCompositeRepository<Song>(listOf(songMediaStoreRepository)) {
    override fun getAlbumSongs(albumId: UUID): List<Song> {
        return getAll().filter { it.albumId == albumId }
    }

    override fun getLocalSongs(): StateFlow<List<Song>> {
        return songMediaStoreRepository.getFlow()
    }
}