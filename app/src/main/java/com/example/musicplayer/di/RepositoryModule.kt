package com.example.musicplayer.di

import com.example.musicplayer.data.repositories.AlbumRepositoryImpl
import com.example.musicplayer.data.repositories.ArtistRepositoryImpl
import com.example.musicplayer.data.repositories.SettingsRepositoryImpl
import com.example.musicplayer.data.repositories.SongRepositoryImpl
import com.example.musicplayer.domain.repositories.AlbumRepository
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SettingsRepository
import com.example.musicplayer.domain.repositories.SongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindSongRepository(
        analyticsServiceImpl: SongRepositoryImpl
    ): SongRepository
    @Binds
    abstract fun bindArtistRepository(
        artistRepositoryImpl: ArtistRepositoryImpl
    ): ArtistRepository
    @Binds
    abstract fun bindAlbumRepository(
        albumRepositoryImpl: AlbumRepositoryImpl
    ): AlbumRepository
    @Binds
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}