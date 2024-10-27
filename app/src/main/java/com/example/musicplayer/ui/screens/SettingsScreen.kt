package com.example.musicplayer.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.R
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateFolderSettings: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(stringResource(id = R.string.settings_title)) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(id = R.string.back))
                }
            }
        )}
    ) {
        Column(modifier = Modifier.padding(it)) {
            SettingsGroup(
                stringResource(id = R.string.music_folders),
                onNavigateFolderSettings,
                Modifier.padding(vertical = 10.dp),
                icon = Icons.Default.Folder)
        }
    }
}

@Composable
fun SettingsGroup(name: String, onClick: () -> Unit, modifier: Modifier = Modifier, icon: ImageVector? = null) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp)
            .clickable(onClick = onClick)
            .then(modifier)
    ) {
        if(icon != null) {
            Icon(imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }else{
            Spacer(modifier = Modifier.width(20.dp))
        }
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .weight(1F)
            )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = stringResource(id = R.string.proceed)
            )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun SettingsScreenPreview() {
    MusicPlayerTheme {
        SettingsScreen({}, {})
    }
}