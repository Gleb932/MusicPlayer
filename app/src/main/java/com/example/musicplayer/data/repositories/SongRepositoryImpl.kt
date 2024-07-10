package com.example.musicplayer.data.repositories

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.Mapper
import com.example.musicplayer.data.MediaStoreScanner
import com.example.musicplayer.data.db.SongDao
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.SongRepository
import okio.IOException
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepositoryImpl,
    private val songDao: SongDao,
    private val context: Context,
    private val mapper: Mapper
): SongRepository {
    private val mediaStoreScanner = MediaStoreScanner(settingsRepository.folders, context, this::scanMediaStore)
    private val mediaMetadataRetriever = MediaMetadataRetriever()
    private var localSongsCache: List<Song>? = null

    override fun getSavedSongs(): List<Song> {
        /*TODO("Implement getting of saved songs")*/
        return listOf()
    }

    override fun getLocalSongs(): List<Song> {
        localSongsCache = localSongsCache ?: mediaStoreScanner.getLocalFiles()
        val localSongsCache = localSongsCache ?: mediaStoreScanner.getLocalFiles()
        return localSongsCache
    }

    override fun getThumbnail(song: Song, size: Pair<Int, Int>): Bitmap? {
        val source = mapper.getSource(song.id) ?: return null
        if(source == DataSource.MEDIA_STORE) {
            val uri = mapper.getMediaStoreId(song.id)?.let { Uri.parse(it) } ?: return null
            val thumbnail = if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)) {
                try {
                    context.contentResolver.loadThumbnail(
                        uri,
                        Size(size.first, size.second), null
                    )
                }catch (ioe: IOException){
                    ioe.printStackTrace()
                    return null
                }
            } else {
                song.sourceUri?.let { mediaMetadataRetriever.setDataSource(it) } ?: return null
                try {
                    val coverImage = mediaMetadataRetriever.embeddedPicture ?: return null
                    BitmapFactory.decodeByteArray(coverImage, 0, coverImage.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
            }
            return thumbnail
        }
        return null
    }

    private fun scanMediaStore(uri: Uri): List<Song> {
        return context.contentResolver.query(
            uri,
            arrayOf(
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.YEAR,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.IS_MUSIC
            ),
            "${MediaStore.Audio.Media.IS_MUSIC} != 0",
            null,
            null
        )?.use { cursor ->
            val res: MutableList<Song> = mutableListOf()
            while (cursor.moveToNext()) {
                val title = cursor.getString(0)
                val year = cursor.getInt(1)
                val data = cursor.getString(2)
                val duration = cursor.getInt(3)
                val id = cursor.getLong(4)
                val song = Song(title, duration, year, null, data, null)
                res.add(song)
                mapper.register(song.id, DataSource.MEDIA_STORE, ContentUris.withAppendedId(uri, id).toString())
            }
            return res
        } ?: listOf()
    }
}