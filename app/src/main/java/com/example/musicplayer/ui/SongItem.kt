package com.example.musicplayer.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.R
import com.example.musicplayer.ui.states.SongItemUiState


@Composable
fun SongItem(
    songItemUiState: SongItemUiState,
    onClick: (SongItemUiState) -> Unit,
    actions: Map<String, (SongItemUiState) -> Unit>,
    modifier: Modifier = Modifier
)
{
    Row(
        modifier = Modifier
            .heightIn(max = 64.dp)
            .clickable { onClick(songItemUiState) }
            .then(modifier)
    ){
        val notePainter = rememberVectorPainter(image = Icons.Default.MusicNote)
        AsyncImage(
            model = songItemUiState.artUri,
            contentDescription = "song art",
            placeholder = notePainter,
            error = notePainter,
            fallback = notePainter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .align(Alignment.CenterVertically)
                .background(Color.White)
        )
        Column(modifier = Modifier
            .weight(1F)
            .padding(horizontal = 10.dp)
        ) {
            Text(
                songItemUiState.title,
                maxLines = 1
            )
            Text(
                songItemUiState.mainArtist ?: stringResource(id = R.string.unknown_artist),
                fontWeight = FontWeight.Light,
                maxLines = 1
            )
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.actions),
                modifier = Modifier.size(128.dp))
        }
    }
}