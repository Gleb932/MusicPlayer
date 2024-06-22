package com.example.musicplayer.domain

data class Maker(
    val artist: Artist,
    val role: MakerRole
): Entity()