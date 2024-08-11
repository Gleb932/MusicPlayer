package com.example.musicplayer.data.mediastore.repositories

import com.example.musicplayer.data.mediastore.ArtistMediaStoreDAO
import com.example.musicplayer.data.mediastore.mappers.ArtistMapper
import com.example.musicplayer.domain.Artist
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistMediaStoreRepository @Inject constructor(
    private val artistMediaStoreDAO: ArtistMediaStoreDAO
): MediaStoreRepository<Artist>() {
    override fun scanLocalFiles() {
        for(artistEntry in artistMediaStoreDAO.getAll()) {
            val artist = ArtistMapper.toDomain(artistEntry, UUID.randomUUID())
            store(artist, artistEntry.id)
        }
    }
}