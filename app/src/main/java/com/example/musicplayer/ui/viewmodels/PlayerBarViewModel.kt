package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.states.PlayerBarUiState
import com.example.musicplayer.ui.states.PlayerState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class PlayerBarViewModel: ViewModel() {
    val state = PlayerHolder.playerState
        .map { playerState -> mapState(playerState) }
        .stateIn(viewModelScope, SharingStarted.Lazily, mapState(PlayerHolder.playerState.value))

    private fun mapState(playerState: PlayerState): PlayerBarUiState {
        return PlayerBarUiState(
            mediaItem = playerState.currentMediaItem,
            isPlaying = playerState.isPlaying,
            isPaused = playerState.isPaused
        )
    }

    fun onPlayButtonClick() {
        if(state.value.isPlaying) PlayerHolder.pause() else PlayerHolder.play()
    }
}