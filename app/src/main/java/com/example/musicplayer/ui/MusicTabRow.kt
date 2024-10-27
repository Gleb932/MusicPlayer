package com.example.musicplayer.ui

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.musicplayer.R

@Composable
fun MusicTabRow(
    selected: Int,
    onNavigateToSongs: () -> Unit,
    onNavigateToAlbums: () -> Unit,
    onNavigateToArtists: () -> Unit
){
    TabRow(selectedTabIndex = selected) {
        Tab(selected = selected == 0, onClick = {
            onNavigateToSongs()
        }) {
            Text(text = stringResource(R.string.songs_title))
        }
        Tab(selected = selected == 1, onClick = {
            onNavigateToAlbums()
        }) {
            Text(text = stringResource(R.string.albums_title))
        }
        Tab(selected = selected == 2, onClick = {
            onNavigateToArtists()
        }) {
            Text(text = stringResource(R.string.artists_title))
        }
    }
}