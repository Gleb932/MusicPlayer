package com.example.musicplayer.domain

data class Single(
    val song: Song,
    val makers: List<Maker> = listOf()
): Entity()