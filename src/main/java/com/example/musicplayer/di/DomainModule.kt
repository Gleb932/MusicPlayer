package com.example.musicplayer.di

import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.domain.usecases.GetLocalSongsUseCase
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.domain.usecases.GetSongMainArtistsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    @ViewModelScoped
    fun provideGetLocalSongsUseCase(songRepository: SongRepository): GetLocalSongsUseCase {
        return GetLocalSongsUseCase(songRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetSongCoverUseCase(songRepository: SongRepository): GetSongCoverUseCase {
        return GetSongCoverUseCase(songRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetSongMainArtistsUseCase(artistRepository: ArtistRepository): GetSongMainArtistsUseCase {
        return GetSongMainArtistsUseCase(artistRepository)
    }
}