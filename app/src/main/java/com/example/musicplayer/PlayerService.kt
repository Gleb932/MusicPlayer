package com.example.musicplayer

import android.content.Intent
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService

class PlayerService: MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private var appIsClosed = false

    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player).build()
    }

    // The user dismissed the app from the recent tasks
    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player!!
        if (!player.playWhenReady
            || player.mediaItemCount == 0
            || player.playbackState == Player.STATE_ENDED) {
            // Stop the service if not playing, continue playing in the background
            // otherwise.
            stopSelf()
        }
        appIsClosed = true
    }

    // Remember to release the player and media session in onDestroy
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onUpdateNotification(session: MediaSession, startInForegroundRequired: Boolean) {
        var foreground = startInForegroundRequired
        val player = mediaSession?.player!!
        if(appIsClosed && (player.playbackState == Player.STATE_IDLE ||
                    player.playbackState == Player.STATE_ENDED ||
                    player.mediaItemCount == 0 ||
                    !player.playWhenReady)) {
            foreground = false
            stopForeground(STOP_FOREGROUND_DETACH)
        }
        super.onUpdateNotification(session, foreground)
    }
}