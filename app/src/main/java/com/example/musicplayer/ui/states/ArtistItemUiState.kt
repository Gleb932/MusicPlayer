package com.example.musicplayer.ui.states

import android.net.Uri
import java.util.UUID

data class ArtistItemUiState (
    val artistId: UUID,
    val name: String,
    val picUri: Uri?
)