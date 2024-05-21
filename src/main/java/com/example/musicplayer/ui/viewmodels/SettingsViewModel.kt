package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsRepository: SettingsRepository) : ViewModel()  {
    val folders: StateFlow<Set<String>> = settingsRepository.folders.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        setOf()
    )

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