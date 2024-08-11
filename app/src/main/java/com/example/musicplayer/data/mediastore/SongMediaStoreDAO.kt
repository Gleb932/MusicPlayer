package com.example.musicplayer.data.mediastore

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.musicplayer.domain.repositories.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongMediaStoreDAO @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) {
    private val mediaStoreScanner = MediaStoreScanner<SongMediaStoreEntry>(settingsRepository.folders, context)

    fun getAll(): List<SongMediaStoreEntry> {
        return mediaStoreScanner.getLocalFiles(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, this::scanMediaStore)
    }

    fun getById(id: Long): SongMediaStoreEntry? {
        return mediaStoreScanner
            .getLocalFiles(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                this::scanMediaStore,
                "${MediaStore.Audio.AudioColumns._ID} == $id"
            ).firstOrNull()
    }

    fun getByAlbumId(id: Long): List<SongMediaStoreEntry> {
        return mediaStoreScanner
            .getLocalFiles(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                this::scanMediaStore,
                "${MediaStore.Audio.AudioColumns.ALBUM_ID} == $id"
            )
    }

    private fun scanMediaStore(uri: Uri, selection: String?): List<SongMediaStoreEntry> {
        val finalSelection = "${MediaStore.Audio.Media.IS_MUSIC} != 0" + if(selection != null) " AND $selection" else ""
        return context.contentResolver.query(
            uri,
            arrayOf(
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID
            ),
            finalSelection,
            null,
            null
        )?.use { cursor ->
            val res: MutableList<SongMediaStoreEntry> = mutableListOf()
            while (cursor.moveToNext()) {
                val title = cursor.getString(0)
                val year = cursor.getInt(1)
                val data = cursor.getString(2)
                val duration = cursor.getLong(3)
                val id = cursor.getLong(4)
                val albumId = cursor.getLong(5)
                val artistId = cursor.getLong(6)
                res.add(SongMediaStoreEntry(title, ContentUris.withAppendedId(uri, id), id, albumId, artistId, duration, data, year))
            }
            return res
        } ?: listOf()
    }
}