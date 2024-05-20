package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.musicplayer.Playlist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel : ViewModel() {
    private var _playlists = MutableStateFlow<List<Playlist>>(listOf())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()
    var selectedPlaylist: Playlist? = null
}