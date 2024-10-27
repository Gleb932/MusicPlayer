package com.example.musicplayer.ui.states

import android.net.Uri
import java.util.UUID

data class AlbumItemUiState (
    val albumId: UUID,
    val title: String,
    val artUri: Uri?,
    val mainArtist: String? = null
)