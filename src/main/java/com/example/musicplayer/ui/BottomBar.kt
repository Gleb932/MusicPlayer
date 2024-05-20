package com.example.musicplayer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.musicplayer.R

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar() {
        NavigationBarItem(
            icon = { Icon(
                imageVector = Icons.Default.LibraryMusic,
                contentDescription = stringResource(id = R.string.playlists_nav)
            ) },
            onClick = { navController.navigate("main") },
            label = { Text(stringResource(id = R.string.playlists_title)) },
            selected = true
        )
        NavigationBarItem(
            icon = { Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings_nav)
            ) },
            onClick = { navController.navigate("settings") },
            label = { Text(stringResource(id = R.string.settings_title)) },
            selected = false
        )
    }
}