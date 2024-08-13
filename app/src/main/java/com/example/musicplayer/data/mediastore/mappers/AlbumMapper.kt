package com.example.musicplayer.data.mediastore.mappers

import com.example.musicplayer.data.ReadMapper
import com.example.musicplayer.data.mediastore.dao.AlbumMediaStoreEntry
import com.example.musicplayer.domain.Album
import java.util.UUID

object AlbumMapper: ReadMapper<Album, AlbumMediaStoreEntry> {
    override fun toDomain(data: AlbumMediaStoreEntry, id: UUID): Album {
        val album = Album(
            id,
            data.name,
            data.lastYear,
            listOf(),
            null
        )
        return album
    }
}