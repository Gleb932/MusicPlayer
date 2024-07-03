package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.states.PlayerBarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerBarViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<PlayerBarUiState> = MutableStateFlow(PlayerBarUiState(null, false))
    val state = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            PlayerHolder.playerState.onEach {newState ->
                _uiState.update { it.copy(
                    mediaItem = newState.currentMediaItem,
                    isPlaying = newState.isPlaying
                ) }
            }.collect()
        }
    }

    fun onPlayButtonClick() {
        if(state.value.isPlaying) PlayerHolder.pause() else PlayerHolder.play()
    }
}