package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicplayer.ui.states.AlbumItemUiState
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import java.util.UUID

@Composable
fun AlbumCard(
    albumItemUiState: AlbumItemUiState,
    onClick: (AlbumItemUiState) -> Unit,
    actions: Map<String, (AlbumItemUiState) -> Unit>,
    modifier: Modifier = Modifier)
{
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(albumItemUiState) },
        modifier = Modifier
            .then(modifier))
    {
        Column(modifier = Modifier.fillMaxSize()) {
            val notePainter = rememberVectorPainter(image = Icons.Default.MusicNote)
            AsyncImage(
                model = albumItemUiState.artUri,
                contentDescription = "album art",
                placeholder = notePainter,
                error = notePainter,
                fallback = notePainter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
                    .align(Alignment.CenterHorizontally)
                    .background(Color.White)
            )
            Text(
                albumItemUiState.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp, start = 5.dp, end = 5.dp)
            )
            albumItemUiState.mainArtist?.let {
                Text(
                    it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                )
            }
        }
    }
}
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun AlbumCardPreview() {
    MusicPlayerTheme {
        AlbumCard(
            albumItemUiState = AlbumItemUiState(
                UUID.randomUUID(),
                "Title",
                null,
                "Artist"
            ),
            onClick = {},
            mapOf()
        )
    }
}