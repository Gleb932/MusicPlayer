package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musicplayer.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) : ViewModel()  {
    val folders: Flow<Set<String>> = settingsRepository.folders

    fun addFolder(uri : String) {
        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.addFolder(uri)
        }
    }

    fun removeFolder(uri : String) {
        CoroutineScope(Dispatchers.IO).launch {
            settingsRepository.removeFolder(uri)
        }
    }
}