package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.Playlist
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun PlaylistCard(playlist: Playlist, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val cover = playlist.songList.lastOrNull()?.album?.cover
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = Modifier
            .then(modifier))
    {
        Column(modifier = Modifier.fillMaxSize()) {
            if (cover == null)
                Image(imageVector = Icons.Default.MusicNote,
                    contentDescription = stringResource(id = R.string.playlist_cover),
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .sizeIn(minWidth = 128.dp, minHeight = 128.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterHorizontally)
                        .background(Color.White)
                )
            else
                Image(bitmap = cover,
                    contentDescription = stringResource(id = R.string.playlist_cover),
                    modifier = Modifier
                        .sizeIn(minWidth = 128.dp, minHeight = 128.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterHorizontally)
                )
            Text(playlist.name, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(all = 5.dp)
            )
        }
    }
}
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PlaylistCardPreview() {
    MusicPlayerTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
            items(1){PlaylistCard(Playlist("Test card", listOf()), {})}
        }
    }
}