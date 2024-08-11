package com.example.musicplayer.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.ui.viewmodels.LocalMusicScreenViewModel

@Composable
fun LocalMusicScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    localMusicScreenViewModel: LocalMusicScreenViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar =
        {
            Column {
                TopSearchBar(stringResource(id = R.string.local_title), {})
                TabRow(selectedTabIndex = 0) {
                    Tab(selected = navController.currentDestination?.route == "songs", onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.songs_title))
                    }
                    Tab(selected = navController.currentDestination?.route == "albums", onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.albums_title))
                    }
                    Tab(selected = navController.currentDestination?.route == "artists", onClick = { /*TODO*/ }) {
                        Text(text = stringResource(R.string.artists_title))
                    }
                }
            }
        },
        bottomBar =
        {
            Column {
                PlayerBar(navController = navController)
                BottomBar(navController = navController)
            }
        },
    ) {
        val songListUiState by localMusicScreenViewModel.songListUiState.collectAsStateWithLifecycle()
        SongList(
            uiState = songListUiState,
            play =
            {
                localMusicScreenViewModel.select(it)
            },
            modifier = Modifier.padding(it)
        )
    }
}