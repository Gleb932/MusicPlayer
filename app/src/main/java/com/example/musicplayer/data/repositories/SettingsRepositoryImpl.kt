package com.example.musicplayer.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.musicplayer.data.SettingsKeys
import com.example.musicplayer.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    override val folders = dataStore.data.map { preferences -> preferences[SettingsKeys.FOLDERS] ?: setOf() }

    override suspend fun addFolder(uri : String) {
        dataStore.edit { preferences ->
            val folders = preferences[SettingsKeys.FOLDERS] ?: setOf()
            preferences[SettingsKeys.FOLDERS] = folders + uri
        }
    }

    override suspend fun removeFolder(uri : String) {
        dataStore.edit { preferences ->
            val folders = preferences[SettingsKeys.FOLDERS] ?: return@edit
            preferences[SettingsKeys.FOLDERS] = folders - uri
        }
    }
}