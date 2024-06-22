package com.example.musicplayer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Save
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
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(id = R.string.saved_nav)
            ) },
            onClick = { navController.navigate("saved") },
            label = { Text(stringResource(id = R.string.saved_title)) },
            selected = navController.currentDestination?.parent?.route == "saved"
        )
        NavigationBarItem(
            icon = { Icon(
                imageVector = Icons.Default.FolderOpen,
                contentDescription = stringResource(id = R.string.local_nav)
            ) },
            onClick = { navController.navigate("local") },
            label = { Text(stringResource(id = R.string.local_title)) },
            selected = navController.currentDestination?.parent?.route == "local"
        )
        NavigationBarItem(
            icon = { Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings_nav)
            ) },
            onClick = { navController.navigate("settings") },
            label = { Text(stringResource(id = R.string.settings_title)) },
            selected = navController.currentDestination?.parent?.route == "settings"
        )
    }
}