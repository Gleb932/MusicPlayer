package com.example.musicplayer.ui.viewmodels

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.formatMillis
import com.example.musicplayer.ui.states.PlayerScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@UnstableApi
@HiltViewModel
class PlayerScreenViewModel @Inject constructor(
    val getSongCoverUseCase: GetSongCoverUseCase,
): ViewModel() {
    private val position: MutableStateFlow<Long> = MutableStateFlow(0)
    val uiState = PlayerHolder.playerState
        .combine(position) { a, b -> Pair(a, b) }
        .map { (state, position) ->
            PlayerScreenUiState(
                currentMedia = state.currentMediaItem,
                title = state.currentMediaItem?.mediaMetadata?.title
                    ?.let { it.toString() } ?: "",
                artist = state.currentMediaItem?.mediaMetadata?.artist
                    ?.let { it.toString() } ?: "Unknown artist",
                bigCover = state.currentSong?.song?.let
                {
                    getSongCoverUseCase(it, Pair(1000, 1000))?.asImageBitmap()
                },
                isPlaying = state.isPlaying,
                duration = formatMillis(state.duration),
                position = formatMillis(position),
                progress = position.toFloat() / state.duration
        ) }
        .stateIn(viewModelScope, SharingStarted.Lazily, PlayerScreenUiState())
    private var trackingJob: Job? = null

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
}