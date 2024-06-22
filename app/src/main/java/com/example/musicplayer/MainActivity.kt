package com.example.musicplayer

import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicplayer.domain.usecases.GetSongCoverUseCase
import com.example.musicplayer.ui.NavigationRoot
import com.example.musicplayer.ui.PlayerState
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ExecutionException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //The future must be kept by callers until the future is complete to get the controller instance.
    //Otherwise, the future might be garbage collected and the listener added by addListener would never be called.
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    @Inject
    lateinit var getSongCoverUseCase: GetSongCoverUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Toast.makeText(
                        this,
                        "The app won't be able to scan default audio folders",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(android.Manifest.permission.READ_MEDIA_AUDIO)
        }else{
            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        PlayerState.getSongCoverUseCase = getSongCoverUseCase
        val sessionToken = SessionToken(this, ComponentName(this, PlayerService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener({
            // MediaController is available here with controllerFuture.get()
            try {
                PlayerState.mediaController = controllerFuture.get()
                // The session accepted the connection.
            } catch (e: ExecutionException) {
                Log.e("MediaController", "Failed to create")
            }
        }, MoreExecutors.directExecutor())
        setContent {
            MusicPlayerTheme {
                NavigationRoot()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        MediaController.releaseFuture(controllerFuture)
    }
}