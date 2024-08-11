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
import com.example.musicplayer.data.LocalFilesScanner
import com.example.musicplayer.domain.repositories.ArtistRepository
import com.example.musicplayer.domain.repositories.SongRepository
import com.example.musicplayer.ui.NavigationRoot
import com.example.musicplayer.ui.PlayerHolder
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //The future must be kept by callers until the future is complete to get the controller instance.
    //Otherwise, the future might be garbage collected and the listener added by addListener would never be called.
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    @Inject
    lateinit var songRepository: SongRepository
    @Inject
    lateinit var artistRepository: ArtistRepository
    @Inject
    lateinit var localFilesScanner: LocalFilesScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    CoroutineScope(Dispatchers.IO).launch {
                        localFilesScanner.scanLocalFiles()
                    }
                } else {
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
        PlayerHolder.songRepository = songRepository
        PlayerHolder.artistRepository = artistRepository
        setContent {
            MusicPlayerTheme {
                NavigationRoot()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(this, ComponentName(this, PlayerService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture.addListener({
            // MediaController is available here with controllerFuture.get()
            try {
                PlayerHolder.mediaController = controllerFuture.get()
                PlayerHolder.mediaController!!.addListener(PlayerHolder)
                // The session accepted the connection.
            } catch (e: ExecutionException) {
                Log.e("MediaController", "Failed to create")
            }
        }, MoreExecutors.directExecutor())
    }

    override fun onStop() {
        super.onStop()
        PlayerHolder.mediaController?.removeListener(PlayerHolder)
        PlayerHolder.mediaController = null
        MediaController.releaseFuture(controllerFuture)
    }
}