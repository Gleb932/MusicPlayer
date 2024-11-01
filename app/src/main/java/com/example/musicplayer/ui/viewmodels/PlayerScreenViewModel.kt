package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.formatMillis
import com.example.musicplayer.ui.states.PlayerScreenUiState
import com.example.musicplayer.ui.states.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PlayerScreenViewModel: ViewModel() {
    private val position: MutableStateFlow<Long> = MutableStateFlow(0)
    val uiState = PlayerHolder.playerState
        .combine(position) { a, b -> Pair(a, b) }
        .map { (playerState, position) -> mapState(playerState, position)}
        .stateIn(viewModelScope, SharingStarted.Lazily, mapState(PlayerHolder.playerState.value, 0))
    private var trackingJob: Job? = null

    private fun mapState(playerState: PlayerState, position: Long): PlayerScreenUiState {
        return PlayerScreenUiState(
            currentMedia = playerState.currentMediaItem,
            title = playerState.currentMediaItem?.mediaMetadata?.title
                ?.let { it.toString() } ?: "",
            artist = playerState.currentMediaItem?.mediaMetadata?.artist
                ?.let { it.toString() } ?: "Unknown artist",
            artUri = playerState.currentMediaItem?.mediaMetadata?.artworkUri,
            isPlaying = playerState.isPlaying,
            duration = formatMillis(playerState.duration),
            position = formatMillis(position),
            progress = position.toFloat() / playerState.duration,
            canSkipPrevious = PlayerHolder.mediaController?.hasPreviousMediaItem() ?: false,
            canSkipNext = PlayerHolder.mediaController?.hasNextMediaItem() ?: false
        )
    }

    fun startTrackingPosition() {
        if(trackingJob != null) return
        trackingJob = viewModelScope.launch {
            while (true) {
                position.update { PlayerHolder.mediaController?.currentPosition ?: 0 }
                delay(200)
            }
        }
    }

    fun stopTrackingPosition() {
        trackingJob?.cancel()
        trackingJob = null
    }

    fun onSliderMove(progress: Float) {
        val mediaController = PlayerHolder.mediaController ?: return
        mediaController.seekTo((progress * mediaController.duration).toLong())
    }

    fun onPlay() {
        PlayerHolder.play()
    }

    fun onPause() {
        PlayerHolder.pause()
    }

    fun onSkipNext() {
        PlayerHolder.skipNext()
    }

    fun onSkipPrevious() {
        PlayerHolder.skipPrevious()
    }
}