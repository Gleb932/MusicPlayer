package com.example.musicplayer.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.navigation.NavController
import com.example.musicplayer.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(navController: NavController) {
    var duration by remember { mutableLongStateOf(PlayerState.mediaController?.duration ?: 0) }
    var isPlaying by remember { mutableStateOf(true) }
    val playerListener = remember {
        object : Player.Listener{
            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
                duration = PlayerState.mediaController?.duration ?: 0
            }

            override fun onIsPlayingChanged(isPlayingNew: Boolean) {
                super.onIsPlayingChanged(isPlayingNew)
                isPlaying = isPlayingNew
            }
        }
    }
    var timeMil by remember { mutableLongStateOf(0) }
    var progress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                delay(1000)
                timeMil = PlayerState.mediaController?.currentPosition ?: 0
                progress = PlayerState.getProgress()
            }
        }
    }
    DisposableEffect(Unit) {
        PlayerState.mediaController?.addListener(playerListener)
        onDispose {
            PlayerState.mediaController?.removeListener(playerListener)
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxSize()
        ) {
            DisposableEffect(Unit) {
                PlayerState.mediaController?.addListener(playerListener)
                onDispose {
                    PlayerState.mediaController?.removeListener(playerListener)
                }
            }
            val bigCover by remember {
                mutableStateOf(
                    PlayerState.getSongCoverUseCase(
                        PlayerState.currentSong.value!!.song,
                        Pair(1000, 1000)
                    )?.asImageBitmap()
                )
            }
            if (bigCover != null) {
                Image(
                    bigCover!!,
                    contentDescription = "song cover",
                    modifier = Modifier
                        .aspectRatio(1F)
                        .fillMaxWidth()
                        .padding(30.dp)
                )
            } else {
                Image(Icons.Default.MusicNote,
                    contentDescription = "song cover",
                    modifier = Modifier.aspectRatio(1F)
                        .fillMaxWidth()
                        .padding(30.dp)
                        .background(Color.White)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(horizontal = 10.dp)) {
                Text(PlayerState.formatMillis(timeMil))
                Slider(progress, onValueChange = {
                    PlayerState.seekTo(it)
                    progress = it
                }, modifier = Modifier.weight(1f))
                Text(PlayerState.formatMillis(duration))
            }
            IconButton(
                onClick = if(isPlaying) PlayerState::pause else PlayerState::play,
            ) {
                Icon(
                    imageVector = if(isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = stringResource(id = R.string.play),
                    modifier = Modifier.size(128.dp))
            }
        }
    }
}