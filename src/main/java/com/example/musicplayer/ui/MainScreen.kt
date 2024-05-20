package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.musicplayer.ui.theme.MusicPlayerTheme
import com.example.musicplayer.ui.viewmodels.MainScreenViewModel

@Composable
fun MainScreen(navController: NavController, modifier: Modifier = Modifier, mainScreenViewModel: MainScreenViewModel = viewModel()) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar() },
        bottomBar = { BottomBar(navController = navController) }
    ){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier
                .padding(it)
            .fillMaxSize())
        {
            items(items = mainScreenViewModel.playlists.value) { playlist ->
                PlaylistCard(
                    playlist,
                    {},
                    Modifier.padding(5.dp)
                )
            }
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
fun MainScreenPreview() {
    MusicPlayerTheme {
        MainScreen(rememberNavController())
    }
}