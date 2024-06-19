package com.example.musicplayer.domain.repositories

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val folders: Flow<Set<String>>
    suspend fun addFolder(uri : String)
    suspend fun removeFolder(uri : String)
}