package com.example.musicplayer.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.core.graphics.drawable.toDrawable
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.Fetcher
import coil.request.Options
import coil.size.pxOrElse
import java.io.IOException

class SongArtFetcher(
    private val context: Context,
    private val uri: Uri,
    private val size: Size
): Fetcher {
    override suspend fun fetch(): DrawableResult? {
        val thumbnail = if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)) {
            try {
                context.contentResolver.loadThumbnail(
                    uri,
                    size,
                    null
                )
            } catch (ioe: IOException) {
                ioe.printStackTrace()
                return null
            }
        } else {
            val mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(context, uri)
            try {
                val coverImage = mediaMetadataRetriever.embeddedPicture ?: return null
                BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return DrawableResult(
            thumbnail.toDrawable(context.resources),
            false,
            DataSource.DISK
        )
    }

    class Factory : Fetcher.Factory<Uri> {
        override fun create(data: Uri, options: Options, imageLoader: ImageLoader): Fetcher? {
            return SongArtFetcher(
                options.context,
                data,
                Size(options.size.width.pxOrElse { return null }, options.size.height.pxOrElse { return null })
            )
        }
    }
}