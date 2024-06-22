package com.example.musicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.media3.session.MediaController
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.ui.states.SongItemUiState
import kotlin.math.roundToLong
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object PlayerState {
    var mediaController: MediaController? = null
    val currentSong: MutableState<SongItemUiState?> = mutableStateOf(null)
    lateinit var getSongCoverUseCase: GetSongCoverUseCase

    fun select(songItem: SongItemUiState) {
        currentSong.value = songItem
    }

    fun play() {
        mediaController?.play()
    }

    fun pause() {
        mediaController?.pause()
    }

    fun seekTo(progress: Float) {
        mediaController?.seekTo(progressToMil(progress))
    }

    @SuppressLint("DefaultLocale")
    fun formatMillis(millis: Long): String {
        val duration = millis.toDuration(DurationUnit.MILLISECONDS)
        return (if(duration.inWholeHours > 0) "${duration.inWholeHours}:" else "") + (duration.inWholeMinutes%60).toString() + String.format(":%02d", duration.inWholeSeconds % 60)
    }

    fun progressToMil(progress: Float): Long {
        return (progress * (mediaController?.duration ?: 1)).roundToLong()
    }

    fun getProgress(): Float {
        return ((mediaController?.currentPosition ?: 0).toFloat() / (mediaController?.duration ?: 1))
    }
}