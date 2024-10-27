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
import com.example.musicplayer.ui.states.ArtistItemUiState
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import java.util.UUID

@Composable
fun ArtistCard(
    artistItemUiState: ArtistItemUiState,
    onClick: (ArtistItemUiState) -> Unit,
    actions: Map<String, (ArtistItemUiState) -> Unit>,
    modifier: Modifier = Modifier)
{
    Card(
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(artistItemUiState) },
        modifier = Modifier
            .then(modifier))
    {
        Column(modifier = Modifier.fillMaxSize()) {
            val notePainter = rememberVectorPainter(image = Icons.Default.MusicNote)
            AsyncImage(
                model = artistItemUiState.picUri,
                contentDescription = "artist",
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
                artistItemUiState.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(5.dp)
            )
        }
    }
}
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun ArtistCardPreview() {
    MusicPlayerTheme {
        ArtistCard(
            artistItemUiState = ArtistItemUiState(
                UUID.randomUUID(),
                "Artist",
                null
            ),
            onClick = {},
            mapOf()
        )
    }
}