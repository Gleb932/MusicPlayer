package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LocalFilesScanner
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.ui.states.ArtistItemUiState
import com.example.musicplayer.ui.states.ArtistsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocalArtistsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val artistRepository: ArtistRepository,
    private val localFilesScanner: LocalFilesScanner
): ViewModel() {
    val searchFilter = savedStateHandle.getStateFlow<String?>("searchQuery", null)
    val artistListUiState: StateFlow<ArtistsUiState> = artistRepository.getLocalArtists()
        .combine(searchFilter) { artistList, searchFilter ->
            if(searchFilter == null) artistList
            else
                artistList.filter { artist ->
                    artist.name.contains(searchFilter, true)
                }
        }
        .map { mapState(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, ArtistsUiState())

    private fun mapState(artists: List<Artist>): ArtistsUiState {
        return ArtistsUiState(
            artists.map { artist ->
                ArtistItemUiState(
                    artist.id,
                    artist.name,
                    artist.picUri
                )
            }
        )
    }

    fun onScanArtists() {
        localFilesScanner.scanLocalFiles()
    }
}