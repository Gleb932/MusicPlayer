package com.example.musicplayer.ui

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.viewmodels.LocalMusicScreenViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
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
                val currentSong: SongItemUiState? by remember {
                    PlayerState.currentSong
                }
                if(currentSong != null) {
                    PlayerBar(
                        navController = navController,
                        songItemUiState = currentSong!!,
                        onPlay = PlayerState::play,
                        onPause = PlayerState::pause,
                    )
                }
                BottomBar(navController = navController)
            }
        },
    ) {
        val readPermission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(android.Manifest.permission.READ_MEDIA_AUDIO)
        }else{
            rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        val songListUiState by localMusicScreenViewModel.songListUiState.collectAsStateWithLifecycle()
        LaunchedEffect(readPermission) {
            if (readPermission.status.isGranted) {
                localMusicScreenViewModel.refreshSongsUiState()
            }
        }
        SongList(
            uiState = songListUiState,
            play = localMusicScreenViewModel::select,
            modifier = Modifier.padding(it)
        )
    }
}