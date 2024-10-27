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
import com.example.musicplayer.ui.ArtistCard
import com.example.musicplayer.ui.TopSearchBar
import com.example.musicplayer.ui.states.ArtistItemUiState
import com.example.musicplayer.ui.viewmodels.LocalArtistsScreenViewModel
import java.util.UUID

@Composable
fun ArtistsScreen(
    musicTabRow: @Composable () -> Unit,
    playerBar: @Composable () -> Unit,
    onNavigateToAlbums: (UUID) -> Unit,
    actions: Map<String, (ArtistItemUiState) -> Unit>,
    localArtistsScreenViewModel: LocalArtistsScreenViewModel = hiltViewModel()
) {
    val uiState by localArtistsScreenViewModel.artistListUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Column {
                TopSearchBar(title = stringResource(id = R.string.artists_title), {})
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
            items(uiState.artists.size) { i ->
                ArtistCard(
                    uiState.artists[i],
                    {
                        onNavigateToAlbums(it.artistId)
                    },
                    actions
                )
            }
        }
    }
}