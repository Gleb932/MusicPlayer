package com.example.musicplayer.data.mediastore.repositories

import com.example.musicplayer.data.mediastore.AlbumMediaStoreDAO
import com.example.musicplayer.data.mediastore.mappers.AlbumMapper
import com.example.musicplayer.domain.Album
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumMediaStoreRepository @Inject constructor(
    private val albumMediaStoreDAO: AlbumMediaStoreDAO
): MediaStoreRepository<Album>() {
    override fun scanLocalFiles() {
        for(albumEntry in albumMediaStoreDAO.getAll()) {
            val album = AlbumMapper.toDomain(albumEntry, UUID.randomUUID())
            store(album, albumEntry.id)
        }
    }
}