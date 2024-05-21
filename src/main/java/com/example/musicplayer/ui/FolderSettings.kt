package com.example.musicplayer.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.musicplayer.R
import com.example.musicplayer.ui.viewmodels.SettingsViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderSettings(navController: NavController, viewModel: SettingsViewModel = hiltViewModel())
{
    val folders by viewModel.folders.collectAsStateWithLifecycle(initialValue = setOf())
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocumentTree()) {
        if (it != null) {
            viewModel.addFolder(it.toString())
        }
    }
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(stringResource(id = R.string.folder_settings_title)) },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(id = R.string.back))
                }
            }
        ) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            ElevatedButton(
                onClick = { launcher.launch(null) },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.add_folder_to_scan))
            }
            LazyColumn {
                items(folders.toList()) { folderUri ->
                    FolderItem(
                        folderUri,
                        {
                            viewModel.removeFolder(folderUri)
                        },
                        Modifier.padding(horizontal = 10.dp)
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun FolderItem(uri: String, onDelete: () -> Unit, modifier: Modifier = Modifier)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Text(
            Uri.parse(uri).path.toString(),
            modifier = Modifier.weight(1F)
        )
        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete))
        }
    }
}