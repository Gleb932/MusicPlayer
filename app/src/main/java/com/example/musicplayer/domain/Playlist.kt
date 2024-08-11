package com.example.musicplayer.domain

import java.time.LocalDateTime
import java.util.UUID

data class Playlist(
    override val id: UUID,
    var name: String,
    var created: LocalDateTime,
    var songIds: List<UUID> = listOf()
): Entity