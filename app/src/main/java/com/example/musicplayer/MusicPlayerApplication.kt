package com.example.musicplayer

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.musicplayer.ui.SongArtFetcher
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MusicPlayerApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(SongArtFetcher.Factory())
            }
            .build()
    }
}