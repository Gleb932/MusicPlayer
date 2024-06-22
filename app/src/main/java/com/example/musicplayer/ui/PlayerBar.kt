package com.example.musicplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.ui.states.SongItemUiState

@Composable
fun PlayerBar(
    navController: NavController,
    songItemUiState: SongItemUiState,
    onPlay: () -> Unit, onPause: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPlaying by remember { mutableStateOf(true) }
    val playerListener = remember {
        object : Player.Listener{
            override fun onIsPlayingChanged(isPlayingNew: Boolean) {
                super.onIsPlayingChanged(isPlayingNew)
                isPlaying = isPlayingNew
            }
        }
    }
    DisposableEffect(Unit) {
        PlayerState.mediaController?.addListener(playerListener)
        onDispose {
            PlayerState.mediaController?.removeListener(playerListener)
        }
    }
    Row(
        modifier = Modifier
            .clickable { navController.navigate("player") }
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .then(modifier)
    ){
        if(songItemUiState.cover != null)
        {
            Image(
                songItemUiState.cover,
                contentDescription = stringResource(id = R.string.song_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(64.dp, 64.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column(modifier = Modifier
            .weight(1F)
            .padding(horizontal = 10.dp)
        ) {
            Text(songItemUiState.song.title, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Text(
                if(songItemUiState.artists.isNotEmpty())
                    songItemUiState.artists.joinToString { it.name }
                else
                    stringResource(id = R.string.unknown_artist),
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(
            onClick = if(isPlaying) onPause else onPlay,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = if(isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = stringResource(id = R.string.play),
                modifier = Modifier.size(128.dp))
        }
    }
}