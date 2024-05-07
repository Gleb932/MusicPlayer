package com.example.musicplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.musicplayer.ui.MainScreen
import com.example.musicplayer.ui.theme.MusicPlayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playlists = (1..20).map { it -> Playlist("Test $it", mutableListOf())}
        setContent {
            MusicPlayerTheme {
                MainScreen(playlists.toMutableList())
            }
        }
    }
}