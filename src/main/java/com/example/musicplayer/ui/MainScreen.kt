package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.Playlist
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun MainScreen(playlists: MutableList<Playlist>, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar() }
    ){
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp))
            {
                items(playlists){ playlist -> PlaylistCard(playlist, Modifier.padding(5.dp)) }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MainScreenPreview() {
    MusicPlayerTheme {
        MainScreen(((1..20).map { it -> Playlist("Test $it", mutableListOf())}).toMutableList(), Modifier.background(
            MaterialTheme.colorScheme.primary
        ))
    }
}