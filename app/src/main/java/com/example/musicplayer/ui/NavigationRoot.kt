package com.example.musicplayer.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.routes.Albums
import com.example.musicplayer.ui.routes.Artists
import com.example.musicplayer.ui.routes.FolderSettings
import com.example.musicplayer.ui.routes.Local
import com.example.musicplayer.ui.routes.Player
import com.example.musicplayer.ui.routes.Settings
import com.example.musicplayer.ui.routes.SettingsList
import com.example.musicplayer.ui.routes.Songs
import com.example.musicplayer.ui.screens.AlbumsScreen
import com.example.musicplayer.ui.screens.ArtistsScreen
import com.example.musicplayer.ui.screens.PlayerScreen
import com.example.musicplayer.ui.screens.SettingsScreen
import com.example.musicplayer.ui.screens.SongsScreen

@Composable
fun NavigationRoot() {
    val navController = rememberNavController()
    val navBack: () -> Unit = { navController.popBackStack() }
    val navOptionsBuilder = { navOptionsBuilder: NavOptionsBuilder ->
        navOptionsBuilder.popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        navOptionsBuilder.launchSingleTop = true
        navOptionsBuilder.restoreState = true
    }
    val musicTabRow = { selected: Int ->
        @Composable {
            MusicTabRow(
                selected,
                onNavigateToSongs = { navController.navigate(Songs(), builder = navOptionsBuilder) },
                onNavigateToAlbums = { navController.navigate(Albums(), builder = navOptionsBuilder) },
                onNavigateToArtists = { navController.navigate(Artists, builder = navOptionsBuilder) }
            )
        }
    }
    val playerBar = @Composable {
        PlayerBar(onNavigateToPlayer = { navController.navigate(Player) })
    }
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Local,
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            modifier = Modifier.padding(padding)
        ) {
            composable<Player> { PlayerScreen() }
            navigation<Local>(startDestination = Songs()) {
                composable<Songs> {
                    SongsScreen(
                        musicTabRow(0),
                        playerBar,
                        mapOf()
                    )
                }
                composable<Albums> {
                    AlbumsScreen(
                        musicTabRow(1),
                        playerBar,
                        { navController.navigate(Songs(albumId = it.toString())) },
                        mapOf()
                    )
                }
                composable<Artists> {
                    ArtistsScreen(
                        musicTabRow(2),
                        playerBar,
                        { navController.navigate(Albums(artistId = it.toString())) },
                        mapOf()
                    )
                }
            }
            navigation<Settings>(SettingsList) {
                composable<SettingsList> {
                    SettingsScreen(
                        navBack,
                        { navController.navigate(FolderSettings) }
                    )
                }
                composable<FolderSettings> {
                    com.example.musicplayer.ui.screens.FolderSettings(
                        navBack
                    )
                }
            }
        }
    }
}