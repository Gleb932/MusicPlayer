package com.example.musicplayer.ui

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.musicplayer.ui.states.PlayerState
import com.example.musicplayer.ui.states.SongItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object PlayerHolder: Player.Listener {
    var mediaController: MediaController? = null
    private val _playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    private fun mediaItemFromSongItem(songItem: SongItemUiState): MediaItem? {
        val sourceUri = songItem.song.sourceUri ?: return null
        val mediaMetadataBuilder = MediaMetadata.Builder()
            .setTitle(songItem.song.title)
            .setArtist(songItem.artists.joinToString{it.name})
        songItem.cover?.let { mediaMetadataBuilder.setArtworkData(
            it.asAndroidBitmap().toByteArray(),
            MediaMetadata.PICTURE_TYPE_FRONT_COVER)
        }
        val mediaMetadata = mediaMetadataBuilder.build()
        val mediaItemBuilder = MediaItem.Builder()
            .setMediaId(songItem.song.id.toString())
            .setMediaMetadata(mediaMetadata)
        mediaItemBuilder.setUri(sourceUri)
        return mediaItemBuilder.build()
    }

    fun loadPlaylist(songList: List<SongItemUiState>) {
        val mediaItemList = songList.mapNotNull { mediaItemFromSongItem(it) }
        mediaController?.addMediaItems(mediaItemList)
        mediaController?.prepare()
        _playerState.update { playerState ->
            playerState.copy(
                songList = songList,
                mediaItems = mediaItemList
            )
        }
    }

    fun select(songId: String) {
        val index = playerState.value.mediaItems.indexOfFirst{ it.mediaId == songId }
        mediaController?.seekTo(index, 0)
    }

    fun play() {
        mediaController?.play()
    }

    fun pause() {
        mediaController?.pause()
    }

    fun skipNext() {
        mediaController?.seekToNext()
    }

    fun skipPrevious() {
        mediaController?.seekToPrevious()
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        _playerState.update {
            it.copy(isPlaying = isPlaying)
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        _playerState.update {
            val currentSong = it.songList.find { it.song.id.toString() == mediaItem?.mediaId } ?: return
            it.copy(
                currentMediaItem = mediaItem,
                currentSong = currentSong,
                duration = currentSong.song.duration?.toLong() ?: 1
            )
        }
    }
}