package com.example.musicplayer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.R
import com.example.musicplayer.ui.SongItem
import com.example.musicplayer.ui.TopSearchBar
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.viewmodels.LocalSongsScreenViewModel

@Composable
fun SongsScreen(
    musicTabRow: @Composable () -> Unit,
    playerBar: @Composable () -> Unit,
    actions: Map<String, (SongItemUiState) -> Unit>,
    localSongsScreenViewModel: LocalSongsScreenViewModel = hiltViewModel()
) {
    val uiState by localSongsScreenViewModel.songListUiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            Column {
                TopSearchBar(title = stringResource(id = R.string.songs_title), {})
                musicTabRow()
            }
        },
        bottomBar = {
            playerBar()
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(
                key = { it.songId },
                items = uiState.songs
            ) {
                SongItem(it, localSongsScreenViewModel::select, actions)
                HorizontalDivider()
            }
        }
    }
}