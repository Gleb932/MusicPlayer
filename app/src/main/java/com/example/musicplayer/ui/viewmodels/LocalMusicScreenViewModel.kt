package com.example.musicplayer.ui.viewmodels

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.domain.usecases.GetLocalSongsUseCase
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.domain.usecases.GetSongMainArtistsUseCase
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.states.SongsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalMusicScreenViewModel @Inject constructor(
    private val getLocalSongsUseCase: GetLocalSongsUseCase,
    private val getSongCoverUseCase: GetSongCoverUseCase,
    private val getSongMainArtistsUseCase: GetSongMainArtistsUseCase,
): ViewModel() {
    val COVER_SIZE = Pair(64, 64)
    private val _songListUiState = MutableStateFlow(SongsUiState())
    val songListUiState: StateFlow<SongsUiState> = _songListUiState.asStateFlow()

    fun refreshSongsUiState() {
        viewModelScope.launch {
            val songs = getLocalSongsUseCase().map { song ->
                SongItemUiState(
                    song,
                    getSongMainArtistsUseCase(song),
                    getSongCoverUseCase(song, COVER_SIZE)?.asImageBitmap()
                )
            }
            _songListUiState.update {
                it.copy(songs = songs)
            }
            PlayerHolder.loadPlaylist(songs)
        }
    }

    fun select(songItem: SongItemUiState){
        PlayerHolder.select(songItem.song.id.toString())
        PlayerHolder.play()
    }
}