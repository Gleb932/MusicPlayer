package com.example.musicplayer.data.mediastore.dao

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.example.musicplayer.domain.repositories.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistMediaStoreDAO @Inject constructor(
    private val settingsRepository: SettingsRepository,
    @ApplicationContext private val context: Context
) {
    private val mediaStoreScanner = MediaStoreScanner<ArtistMediaStoreEntry>(settingsRepository.folders, context)

    fun getAll(): List<ArtistMediaStoreEntry> {
        return mediaStoreScanner.getLocalFiles(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, this::scanMediaStore)
    }

    fun getById(id: Long): ArtistMediaStoreEntry? {
        return mediaStoreScanner
            .getLocalFiles(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                this::scanMediaStore,
                "${MediaStore.Audio.Artists._ID} == $id"
            ).firstOrNull()
    }

    private fun scanMediaStore(uri: Uri, selection: String?): List<ArtistMediaStoreEntry> {
        return context.contentResolver.query(
            uri,
            arrayOf(
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.Artists._ID
            ),
            selection,
            null,
            null
        )?.use { cursor ->
            val res: MutableList<ArtistMediaStoreEntry> = mutableListOf()
            while (cursor.moveToNext()) {
                val name = cursor.getString(0)
                val id = cursor.getLong(1)
                res.add(ArtistMediaStoreEntry(name, ContentUris.withAppendedId(uri, id), id))
            }
            return res
        } ?: listOf()
    }
}