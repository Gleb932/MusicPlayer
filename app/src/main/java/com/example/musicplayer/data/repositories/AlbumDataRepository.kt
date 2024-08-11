package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.LocalFilesSource
import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.repositories.AlbumRepository

interface AlbumDataRepository: AlbumRepository, LocalFilesSource, CompositeRepository<Album>