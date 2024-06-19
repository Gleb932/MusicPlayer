package com.example.musicplayer.data.repositories

import android.content.Context
import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.Mapper
import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val context: Context,
    private val mapper: Mapper
): AlbumRepository {
    override fun getSongAlbum(song: Song): Album? {
        val source = mapper.getSource(song.id)
        when(source) {
            DataSource.ROOM -> TODO()
            DataSource.MEDIA_STORE -> TODO()
            null -> TODO()
        }
    }

    override fun getLocalAlbums(): List<Album> {
        TODO("Not yet implemented")
    }

    override fun getSavedAlbums(): List<Album> {
        TODO("Not yet implemented")
    }
}