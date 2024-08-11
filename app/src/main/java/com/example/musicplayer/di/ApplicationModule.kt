package com.example.musicplayer.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.musicplayer.data.db.AppDatabase
import com.example.musicplayer.data.db.SongDao
import com.example.musicplayer.data.repositories.SettingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext appContext: Context): SettingsRepositoryImpl {
        return SettingsRepositoryImpl(appContext.dataStore)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
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