package com.example.musicplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.states.SongItemUiState
import com.example.musicplayer.ui.states.SongsUiState

@Composable
fun SongList(
    uiState: SongsUiState,
    play: (SongItemUiState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            key = {it.song.id},
            items = uiState.songs
        ){
            SongItem(it, play)
            HorizontalDivider()
        }
    }
}

@Composable
fun SongItem(songItemUiState: SongItemUiState, onClick: (SongItemUiState) -> Unit, modifier: Modifier = Modifier)
{
    Row(
        modifier = Modifier
            .clickable{ onClick(songItemUiState) }
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
        }else{
            Image(
                imageVector = Icons.Default.MusicNote,
                contentDescription = stringResource(id = R.string.song_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .background(color = Color.White)
                    .size(64.dp, 64.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column(modifier = Modifier
            .weight(1F)
            .padding(horizontal = 10.dp)
        ) {
            Text(songItemUiState.song.title)
            Text(
                if(songItemUiState.mainArtist != null)
                    songItemUiState.mainArtist.name
                else
                    stringResource(id = R.string.unknown_artist),
                fontWeight = FontWeight.Light
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