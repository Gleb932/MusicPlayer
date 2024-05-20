package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.Song
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@Composable
fun SongList(songList: List<Song>, modifier: Modifier = Modifier)
{
    LazyColumn(modifier = modifier) {
        items(songList){
            SongItem(it)
            HorizontalDivider()
        }
    }
}

@Composable
fun SongItem(song: Song, modifier: Modifier = Modifier)
{
    val cover = song.album?.cover
    Row(modifier = modifier){
        if(cover != null)
        {
            Image(
                cover,
                contentDescription = stringResource(id = R.string.song_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                    .align(Alignment.CenterVertically)
            )
        }else{
            Image(
                imageVector = Icons.Default.MusicNote,
                contentDescription = stringResource(id = R.string.song_cover),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .sizeIn(maxWidth = 100.dp, maxHeight = 100.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Column(modifier = Modifier
            .weight(1F)
            .padding(horizontal = 10.dp)
        ) {
            Text(song.title)
            Text(song.artists.firstOrNull() ?: stringResource(id = R.string.unknown_artist), fontWeight = FontWeight.Light)
        }
        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.actions),
                modifier = Modifier.size(128.dp))
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
fun SongListPreview() {
    MusicPlayerTheme {
        SongList(songList = listOf(
            Song("test1", listOf("Test artist", "test 3")),
            Song("test2", listOf("Test artist 2", "test 3"))
        )
        )
    }
}