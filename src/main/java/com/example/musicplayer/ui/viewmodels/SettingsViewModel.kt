package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.usecases.AddMusicFolderUseCase
import com.example.musicplayer.domain.usecases.GetMusicFoldersUseCase
import com.example.musicplayer.domain.usecases.RemoveMusicFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getMusicFoldersUseCase: GetMusicFoldersUseCase,
    private val addMusicFolderUseCase: AddMusicFolderUseCase,
    private val removeMusicFolderUseCase: RemoveMusicFolderUseCase
) : ViewModel()  {
    val folders: StateFlow<Set<String>> = getMusicFoldersUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        setOf()
    )

    fun addFolder(uri : String) {
        CoroutineScope(Dispatchers.IO).launch {
            addMusicFolderUseCase(uri)
        }
    }

    fun removeFolder(uri : String) {
        CoroutineScope(Dispatchers.IO).launch {
            removeMusicFolderUseCase(uri)
        }
    }
}