package com.example.musicplayer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.musicplayer.data.Mapper
import com.example.musicplayer.data.db.AppDatabase
import com.example.musicplayer.data.db.SongDao
import com.example.musicplayer.data.repositories.ArtistRepositoryImpl
import com.example.musicplayer.data.repositories.SettingsRepositoryImpl
import com.example.musicplayer.data.repositories.SongRepositoryImpl
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SettingsRepository
import com.example.musicplayer.domain.repositories.SongRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    @Singleton
    fun provideMapper(): Mapper {
        return Mapper()
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(dataStore: DataStore<Preferences>): SettingsRepository {
        return SettingsRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideSongRepository(
        settingsRepository: SettingsRepositoryImpl,
        songDao: SongDao,
        @ApplicationContext appContext: Context,
        mapper: Mapper
    ): SongRepository {
        return SongRepositoryImpl(settingsRepository, songDao, appContext, mapper)
    }

    @Provides
    @Singleton
    fun provideArtistRepository(
        settingsRepository: SettingsRepositoryImpl,
        @ApplicationContext appContext: Context,
        mapper: Mapper
    ): ArtistRepository {
        return ArtistRepositoryImpl(settingsRepository, appContext, mapper)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSongDao(appDatabase: AppDatabase): SongDao {
        return appDatabase.songDao()
    }
}