package com.example.musicplayer.domain

import android.net.Uri
import java.util.UUID

data class Artist(
    override val id: UUID,
    var name: String,
    var picUri: Uri? = null
): Entity