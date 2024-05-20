package com.example.musicplayer.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private object SettingsKeys {
    val FOLDERS = stringSetPreferencesKey("folders")
}

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
){
    val folders = dataStore.data.map { preferences -> preferences[SettingsKeys.FOLDERS] ?: setOf() }

    suspend fun addFolder(uri : String) {
        dataStore.edit { preferences ->
            val folders = preferences[SettingsKeys.FOLDERS] ?: setOf()
            preferences[SettingsKeys.FOLDERS] = folders + uri
        }
    }

    suspend fun removeFolder(uri : String) {
        dataStore.edit { preferences ->
            val folders = preferences[SettingsKeys.FOLDERS] ?: return@edit
            preferences[SettingsKeys.FOLDERS] = folders - uri
        }
    }
}