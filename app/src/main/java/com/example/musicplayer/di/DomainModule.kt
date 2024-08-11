package com.example.musicplayer.di

import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
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
    fun provideGetSongCoverUseCase(songRepository: SongRepository): GetSongCoverUseCase {
        return GetSongCoverUseCase(songRepository)
    }
}