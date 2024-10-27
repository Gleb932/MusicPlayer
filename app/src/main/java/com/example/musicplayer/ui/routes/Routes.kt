package com.example.musicplayer.ui.routes

import kotlinx.serialization.Serializable


@Serializable
data object Artists

@Serializable
data class Albums(
    val artistId: String? = null
)

@Serializable
data class Songs(
    val albumId: String? = null,
    val artistId: String? = null
)

@Serializable
data object Saved

@Serializable
data object Local

@Serializable
data object Settings

@Serializable
data object SettingsList

@Serializable
data object FolderSettings

@Serializable
data object Player