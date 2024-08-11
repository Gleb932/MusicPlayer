package com.example.musicplayer.ui.viewmodels

import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LocalFilesScanner
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.states.SongsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocalMusicScreenViewModel @Inject constructor(
    private val getSongCoverUseCase: GetSongCoverUseCase,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val localFilesScanner: LocalFilesScanner
): ViewModel() {
    val COVER_SIZE = Pair(64, 64)
    val songListUiState: StateFlow<SongsUiState> = songRepository.getLocalSongs().map {
        mapState(it)
    }.stateIn(viewModelScope, SharingStarted.Lazily, SongsUiState())

    private fun mapState(songs: List<Song>): SongsUiState {
        return SongsUiState(
            songs.map { song ->
                SongItemUiState(
                song,
                song.makers.firstOrNull()?.let { artistRepository.getEntity(it.artistId) },
                getSongCoverUseCase(song, COVER_SIZE)?.asImageBitmap()
            ) }
        )
    }

    fun onScanSongs() {
        localFilesScanner.scanLocalFiles()
    }

    fun select(songItem: SongItemUiState){
        PlayerHolder.select(songItem.song.id)
        PlayerHolder.play()
    }
}