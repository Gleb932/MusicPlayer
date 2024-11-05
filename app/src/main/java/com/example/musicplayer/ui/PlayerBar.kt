package com.example.musicplayer.ui

import android.graphics.BitmapFactory
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.musicplayer.R
import com.example.musicplayer.ui.states.PlayerBarUiState
import com.example.musicplayer.ui.viewmodels.PlayerBarViewModel

@Composable
fun PlayerBar(
    onNavigateToPlayer: () -> Unit,
    modifier: Modifier = Modifier,
    playerBarViewModel: PlayerBarViewModel = hiltViewModel()
) {
    val playerBarUiState by playerBarViewModel.state.collectAsStateWithLifecycle(initialValue = PlayerBarUiState())
    if (!playerBarUiState.isPlaying && !playerBarUiState.isPaused) return
    Row(
        modifier = Modifier
            .clickable(onClick = onNavigateToPlayer)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .then(modifier)
    ){
        val metadata = playerBarUiState.mediaItem?.mediaMetadata
        val art = metadata?.artworkData
        if(art?.isNotEmpty() == true)
        {
            Image(
                BitmapFactory.decodeByteArray(art, 0, art.size).asImageBitmap(),
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
            Text(
                metadata?.title.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1
            )
            Text(
                if(metadata?.artist != null)
                    metadata.artist.toString()
                else
                    stringResource(id = R.string.unknown_artist),
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                maxLines = 1
            )
        }
        IconButton(
            onClick = playerBarViewModel::onPlayButtonClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = if(playerBarUiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = stringResource(id = R.string.play),
                modifier = Modifier.size(128.dp))
        }
    }
}