package com.example.musicplayer.data.mediastore.mappers

import com.example.musicplayer.data.ReadMapper
import com.example.musicplayer.data.mediastore.SongMediaStoreEntry
import com.example.musicplayer.domain.Song
import java.util.UUID

object SongMapper: ReadMapper<Song, SongMediaStoreEntry> {
    override fun toDomain(data: SongMediaStoreEntry, id: UUID): Song {
        val song = Song(
            id,
            data.title,
            data.duration,
            data.year,
            null,
            listOf(),
            listOf(),
            data.filepath,
            null
        )
        return song
    }
}