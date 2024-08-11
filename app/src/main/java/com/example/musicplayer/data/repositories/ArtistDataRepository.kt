package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.LocalFilesSource
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.repositories.ArtistRepository

interface ArtistDataRepository: ArtistRepository, LocalFilesSource, CompositeRepository<Artist>