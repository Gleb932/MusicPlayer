package com.example.musicplayer.data.repositories

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.Mapper
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.ArtistRepository
import javax.inject.Inject


class ArtistRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val context: Context,
    private val mapper: Mapper
): ArtistRepository {
    override fun getSongArtists(song: Song): List<Artist> {
        val source = mapper.getSource(song.id)
        val res = when(source) {
            DataSource.ROOM -> TODO()
            DataSource.MEDIA_STORE -> mapper.getMediaStoreId(song.id)?.let { getMediaStoreSongArtist(it)?.let{ listOf(it) } ?: listOf() }
            null -> TODO()
        }
        return res ?: listOf()
    }

    private fun getMediaStoreSongArtist(uri: String): Artist? {
        return context.contentResolver.query(
            Uri.parse(uri),
            arrayOf(MediaStore.Audio.Media.ARTIST),
            null,
            null,
            null,
            null
        )?.use {
            it.moveToFirst()
            it.getString(0)
        }?.let {
            Artist(it)
        }
    }
}