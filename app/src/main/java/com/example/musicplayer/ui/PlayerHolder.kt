package com.example.musicplayer.ui

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.ui.states.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.UUID

object PlayerHolder: Player.Listener {
    lateinit var songRepository: SongRepository
    lateinit var artistRepository: ArtistRepository
    var mediaController: MediaController? = null
    private val _playerState: MutableStateFlow<PlayerState> = MutableStateFlow(PlayerState())
    private var updateCurrentWhenReady = false

    val playerState: StateFlow<PlayerState> by lazy {
        songRepository.getLocalSongs()
            .combine(_playerState) { songs, state ->
                mapState(songs, state)
            }
            .onEach {
                mediaController?.addMediaItems(it.mediaItems)
                mediaController?.prepare()
            }
            .stateIn(
                CoroutineScope(context = Dispatchers.Main),
                SharingStarted.Lazily,
                PlayerState()
            )
    }

    private fun mapState(songs: List<Song>, playerState: PlayerState): PlayerState {
        return playerState.copy(
                songList = songs,
                mediaItems = songs.mapNotNull { mediaItemFromSong(it) }
            )
    }

    private fun mediaItemFromSong(song: Song): MediaItem? {
        val sourceUri = song.sourceUri ?: return null
        val mediaMetadataBuilder = MediaMetadata.Builder()
            .setTitle(song.title)
        song.makers.firstOrNull()?.artistId?.let {
            mediaMetadataBuilder.setArtist(artistRepository.getEntity(it)?.name)
        }
        song.cover?.let { mediaMetadataBuilder.setArtworkData(
            it.asAndroidBitmap().toByteArray(),
            MediaMetadata.PICTURE_TYPE_FRONT_COVER)
        }
        val mediaMetadata = mediaMetadataBuilder.build()
        val mediaItemBuilder = MediaItem.Builder()
            .setMediaId(song.id.toString())
            .setMediaMetadata(mediaMetadata)
        mediaItemBuilder.setUri(sourceUri)
        return mediaItemBuilder.build()
    }

    fun select(songId: UUID) {
        val index = playerState.value.mediaItems.indexOfFirst{ it.mediaId == songId.toString() }
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

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if(updateCurrentWhenReady && playbackState == Player.STATE_READY) {
            updateCurrent()
            updateCurrentWhenReady = false
        }
    }

    private fun updateCurrent() {
        _playerState.update {
            it.copy(
                currentMediaItem = mediaController?.currentMediaItem,
                duration = mediaController?.duration ?: 1
            )
        }
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        if(reason == Player.MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED) return
        if(mediaController?.playbackState == Player.STATE_READY){
            updateCurrent()
        } else {
            updateCurrentWhenReady = true
        }
    }
}