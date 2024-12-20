package com.example.musicplayer.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicplayer.ui.theme.MusicPlayerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(title: String, onSearch: (String) -> Unit)
{
    val focusRequester = remember { FocusRequester() }
    var searchActive by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(searchActive) { if(searchActive) focusRequester.requestFocus() }
    if(searchActive) {
        var searchText by rememberSaveable {
            mutableStateOf("")
        }
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchText,
                    onQueryChange = { searchText = it },
                    onSearch = { query ->
                        onSearch(query)
                        searchText = ""
                        searchActive = false },
                    false,
                    {},
                    placeholder = { Text(text = "Search") },
                    leadingIcon = {
                        IconButton(onClick = {
                            onSearch(searchText)
                            searchText = ""
                            searchActive = false
                        }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (searchText.isEmpty()) {
                                searchActive = false
                            } else {
                                searchText = ""
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close search"
                            )
                        }
                    },
                    modifier = Modifier.focusRequester(focusRequester)
                )
            },
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth(),
        ){}
    } else {
        TopAppBar(
            title = {
                Text(title)
            },
            actions = {
                IconButton(onClick = { searchActive = true } ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            }
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
fun TopBarPreview()
{
    MusicPlayerTheme {
        TopSearchBar("Playlists", {})
    }
}