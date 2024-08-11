package com.example.musicplayer.domain

import java.util.UUID

data class Artist(
    override val id: UUID,
    var name: String
): Entity