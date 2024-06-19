package com.example.musicplayer.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "local",
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        }) {
        composable("player") { PlayerScreen(navController) }
        navigation("songs", "local")
        {
            composable("songs") { LocalMusicScreen(navController) }
        }
        navigation("songs", "saved")
        {
            composable("songs") {  }
        }
        navigation("settingsList", "settings")
        {
            composable("settingsList") { SettingsScreen(navController) }
            composable("folderSettings") { FolderSettings(navController) }
        }
    }
}