package com.example.musicplayer.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.ui.viewmodels.PlayerScreenViewModel

@OptIn(UnstableApi::class)
@Composable
fun PlayerScreen(
    navController: NavController,
    playerScreenViewModel: PlayerScreenViewModel = hiltViewModel()
) {
    val state by playerScreenViewModel.uiState.collectAsState()
    DisposableEffect(Unit) {
        playerScreenViewModel.startTrackingPosition()
        onDispose {
            playerScreenViewModel.stopTrackingPosition()
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.bigCover != null) {
                Image(
                    state.bigCover!!,
                    contentDescription = "song cover",
                    modifier = Modifier
                        .aspectRatio(1F)
                        .fillMaxWidth()
                        .padding(30.dp)
                )
            } else {
                Image(Icons.Default.MusicNote,
                    contentDescription = "song cover",
                    modifier = Modifier
                        .aspectRatio(1F)
                        .fillMaxWidth()
                        .padding(30.dp)
                        .background(Color.White)
                )
            }
            Text(state.title)
            Text(state.artist)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 10.dp)) {
                Text(state.position)
                Slider(state.progress, onValueChange = playerScreenViewModel::onSliderMove, modifier = Modifier.weight(1f))
                Text(state.duration)
            }
            IconButton(
                onClick = if(state.isPlaying) playerScreenViewModel::onPause else playerScreenViewModel::onPlay,
            ) {
                Icon(
                    imageVector = if(state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = stringResource(id = R.string.play),
                    modifier = Modifier.size(128.dp))
            }
        }
    }
}