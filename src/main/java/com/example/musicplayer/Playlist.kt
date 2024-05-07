package com.example.musicplayer

data class Playlist(var name: String, val songList: MutableList<Song> = mutableListOf())