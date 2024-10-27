package com.example.musicplayer.ui.viewmodels

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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LocalArtistsScreenViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val localFilesScanner: LocalFilesScanner
): ViewModel() {
    val artistListUiState: StateFlow<ArtistsUiState> = artistRepository.getLocalArtists().map {
        mapState(it)
    }.stateIn(viewModelScope, SharingStarted.Lazily, ArtistsUiState())

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