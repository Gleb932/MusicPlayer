package com.example.musicplayer.data.mediastore.mappers

import com.example.musicplayer.data.ReadMapper
import com.example.musicplayer.data.mediastore.dao.ArtistMediaStoreEntry
import com.example.musicplayer.domain.Artist
import java.util.UUID

object ArtistMapper: ReadMapper<Artist, ArtistMediaStoreEntry> {
    override fun toDomain(data: ArtistMediaStoreEntry, id: UUID): Artist {
        val artist = Artist(
            id,
            data.name
        )
        return artist
    }
}