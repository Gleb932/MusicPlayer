package com.example.musicplayer.ui.viewmodels

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.formatMillis
import com.example.musicplayer.ui.states.PlayerScreenUiState
import com.example.musicplayer.ui.states.PlayerState
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
        .map { (playerState, position) -> mapState(playerState, position)}
        .stateIn(viewModelScope, SharingStarted.Lazily, mapState(PlayerHolder.playerState.value, 0))
    private var trackingJob: Job? = null

    private fun mapState(playerState: PlayerState, position: Long): PlayerScreenUiState {
        playerState.currentSong?.bigCover  = playerState.currentSong?.bigCover ?: playerState.currentSong?.song?.let{
            getSongCoverUseCase(it, Pair(1000, 1000))?.asImageBitmap()
        }
        return PlayerScreenUiState(
            currentMedia = playerState.currentMediaItem,
            title = playerState.currentMediaItem?.mediaMetadata?.title
                ?.let { it.toString() } ?: "",
            artist = playerState.currentMediaItem?.mediaMetadata?.artist
                ?.let { it.toString() } ?: "Unknown artist",
            bigCover = playerState.currentSong?.bigCover,
            isPlaying = playerState.isPlaying,
            duration = formatMillis(playerState.duration),
            position = formatMillis(position),
            progress = position.toFloat() / playerState.duration
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