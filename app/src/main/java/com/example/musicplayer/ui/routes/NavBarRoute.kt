package com.example.musicplayer.ui.routes

import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarRoute<T : Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)