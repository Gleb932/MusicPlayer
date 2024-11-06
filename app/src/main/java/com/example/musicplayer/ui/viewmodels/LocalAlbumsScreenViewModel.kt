package com.example.musicplayer.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicplayer.data.LocalFilesScanner
import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.repositories.AlbumRepository
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.ui.states.AlbumItemUiState
import com.example.musicplayer.ui.states.AlbumsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class LocalAlbumsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val localFilesScanner: LocalFilesScanner
): ViewModel() {
    private val artistId = savedStateHandle.getStateFlow<String?>("artistId", null)
    val searchFilter = savedStateHandle.getStateFlow<String?>("searchQuery", null)
    val albumListUiState: StateFlow<AlbumsUiState> = albumRepository.getLocalAlbums()
        .combine(artistId, {
                albumList, artistId -> artistId?.let {
                    albumList.filter {
                        album -> album.makers.any { it.artistId == UUID.fromString(artistId) }
                    }
                }
            ?: albumList
        })
        .combine(searchFilter) { albumList, searchFilter ->
            if(searchFilter == null) albumList
            else
                albumList.filter { album ->
                    album.title.contains(searchFilter, true) ||
                    album.makers.any { maker ->
                        artistRepository.getEntity(maker.artistId)?.name?.contains(searchFilter, true) == true
                    }
                }
        }
        .map { mapState(it) }
        .stateIn(viewModelScope, SharingStarted.Lazily, AlbumsUiState())

    private fun mapState(albums: List<Album>): AlbumsUiState {
        return AlbumsUiState(
            albums.map { album ->
                AlbumItemUiState(
                    album.id,
                    album.title,
                    album.coverUri,
                    album.makers.firstOrNull()?.let { artistRepository.getEntity(it.artistId)?.name },
            ) }
        )
    }

    fun onScanAlbums() {
        localFilesScanner.scanLocalFiles()
    }
}