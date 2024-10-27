package com.example.musicplayer.ui

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.musicplayer.ui.routes.Local
import com.example.musicplayer.ui.routes.NavBarRoute
import com.example.musicplayer.ui.routes.Saved
import com.example.musicplayer.ui.routes.Settings

val routes = listOf(
    NavBarRoute("Saved", Saved, Icons.Default.Save),
    NavBarRoute("Local", Local, Icons.Default.FolderOpen),
    NavBarRoute("Settings", Settings, Icons.Default.Settings)
)

@SuppressLint("RestrictedApi")
@Composable
fun BottomBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    NavigationBar {
        routes.forEach {
            route ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = route.icon,
                        contentDescription = route.name
                    )
                },
                onClick = { navController.navigate(route.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                } },
                label = { Text(route.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route::class) } == true
            )
        }
    }
}