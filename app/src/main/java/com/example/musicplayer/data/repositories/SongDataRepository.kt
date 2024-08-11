package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.LocalFilesSource
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.SongRepository

interface SongDataRepository: SongRepository, LocalFilesSource, CompositeRepository<Song>