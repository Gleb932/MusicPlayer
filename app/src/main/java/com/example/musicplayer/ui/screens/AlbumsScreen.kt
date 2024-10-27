package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.R
import com.example.musicplayer.ui.AlbumCard
import com.example.musicplayer.ui.TopSearchBar
import com.example.musicplayer.ui.states.AlbumItemUiState
import com.example.musicplayer.ui.viewmodels.LocalAlbumsScreenViewModel
import java.util.UUID

@Composable
fun AlbumsScreen(
    musicTabRow: @Composable () -> Unit,
    playerBar: @Composable () -> Unit,
    onNavigateToSongs: (UUID) -> Unit,
    actions: Map<String, (AlbumItemUiState) -> Unit>,
    localAlbumsScreenViewModel: LocalAlbumsScreenViewModel = hiltViewModel()
) {
    val uiState by localAlbumsScreenViewModel.albumListUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Column {
                TopSearchBar(title = stringResource(id = R.string.albums_title), {})
                musicTabRow()
            }
        },
        bottomBar = {
            playerBar()
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.padding(paddingValues).then(
                Modifier.fillMaxWidth()
                    .padding(top = 5.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.albums.size) { i ->
                AlbumCard(
                    uiState.albums[i],
                    {
                        onNavigateToSongs(it.albumId)
                    },
                    actions
                )
            }
        }
    }
}