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
class AlbumMediaStoreDAO @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) {
    private val mediaStoreScanner = MediaStoreScanner<AlbumMediaStoreEntry>(settingsRepository.folders, context)

    fun getAll(): List<AlbumMediaStoreEntry> {
        return mediaStoreScanner.getLocalFiles(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, this::scanMediaStore)
    }

    fun getById(id: Long): AlbumMediaStoreEntry? {
        return mediaStoreScanner
            .getLocalFiles(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                this::scanMediaStore,
                "${MediaStore.Audio.AudioColumns._ID} == $id"
            ).firstOrNull()
    }

    fun getByArtistId(id: Long): List<AlbumMediaStoreEntry> {
        return mediaStoreScanner
            .getLocalFiles(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                this::scanMediaStore,
                "${MediaStore.Audio.AudioColumns.ARTIST_ID} == $id"
            )
    }

    private fun scanMediaStore(uri: Uri, selection: String?): List<AlbumMediaStoreEntry> {
        return context.contentResolver.query(
            uri,
            arrayOf(
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.FIRST_YEAR,
                MediaStore.Audio.Albums.LAST_YEAR,
            ),
            selection,
            null,
            null
        )?.use { cursor ->
            val res: MutableList<AlbumMediaStoreEntry> = mutableListOf()
            while (cursor.moveToNext()) {
                val title = cursor.getString(0)
                val id = cursor.getLong(1)
                val firstYear = cursor.getInt(2)
                val lastYear = cursor.getInt(3)
                res.add(AlbumMediaStoreEntry(title, ContentUris.withAppendedId(uri, id), id, firstYear, lastYear))
            }
            return res
        } ?: listOf()
    }
}