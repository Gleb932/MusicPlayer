package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LocalFilesScanner
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.states.SongsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LocalSongsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val localFilesScanner: LocalFilesScanner
): ViewModel() {
    private val albumId = savedStateHandle.getStateFlow<String?>("albumId", null)
    val searchFilter = savedStateHandle.getStateFlow<String?>("searchQuery", null)
    val songListUiState: StateFlow<SongsUiState> = songRepository.getLocalSongs()
        .combine(albumId) { songList, albumId ->
            albumId?.let {
                songList.filter { song ->
                    song.albumId == UUID.fromString(albumId)
                }
            } ?: songList
        }
        .combine(searchFilter) { songList, searchFilter ->
            if(searchFilter == null) songList
            else
                songList.filter { song ->
                    song.title.contains(searchFilter, true) ||
                    song.makers.any { maker ->
                        artistRepository.getEntity(maker.artistId)?.name?.contains(searchFilter, true) == true
                    }
                }
        }
        .map { songList -> mapState(songList) }
        .stateIn(viewModelScope, SharingStarted.Lazily, SongsUiState())

    private fun mapState(songs: List<Song>): SongsUiState {
        return SongsUiState(
            songs.map { song ->
                SongItemUiState(
                    song.id,
                    song.title,
                    song.coverUri ?: song.sourceUri,
                    song.makers.firstOrNull()?.let { artistRepository.getEntity(it.artistId)?.name },
            ) }
        )
    }

    fun onScanSongs() {
        localFilesScanner.scanLocalFiles()
    }

    fun select(songItem: SongItemUiState){
        PlayerHolder.select(songItem.songId)
        PlayerHolder.play()
    }
}