package com.example.musicplayer.data.mediastore.repositories

import com.example.musicplayer.data.mediastore.SongMediaStoreDAO
import com.example.musicplayer.data.mediastore.mappers.SongMapper
import com.example.musicplayer.domain.Song
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongMediaStoreRepository @Inject constructor(
    private val songMediaStoreDAO: SongMediaStoreDAO
): MediaStoreRepository<Song>() {
    override fun scanLocalFiles() {
        for(songEntry in songMediaStoreDAO.getAll()) {
            val song = SongMapper.toDomain(songEntry, UUID.randomUUID())
            store(song, songEntry.id)
        }
    }
}