package com.example.musicplayer.ui

import android.content.res.Configuration

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicplayer.Playlist
import com.example.musicplayer.Song
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun PlaylistScreen(playlist: Playlist) {
    SongList(songList = playlist.songList)
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PlaylistScreenPreview() {
    MusicPlayerTheme {
        PlaylistScreen(playlist = Playlist("Test", listOf(
            Song(0, "test1", listOf("Test artist", "test 3")),
            Song(0, "test2", listOf("Test artist 2", "test 3"))
        )
        ))
    }
}