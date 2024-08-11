package com.example.musicplayer.domain

import java.util.UUID

data class Maker(
    override val id: UUID,
    var artistId: UUID,
    var role: MakerRole?
): Entity