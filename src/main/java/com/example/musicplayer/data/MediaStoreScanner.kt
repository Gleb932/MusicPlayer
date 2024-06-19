package com.example.musicplayer.data

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.documentfile.provider.DocumentFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MediaStoreScanner<T> (
    private val folders: Flow<Set<String>>,
    private val context: Context,
    private val scan: (Uri) -> List<T>
){
    private var curFolders: Set<String> = setOf()
    init {
        if(context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ||
            context.checkSelfPermission(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                folders.collect { folders ->
                    curFolders = folders
                }
            }
        }
    }

    fun getLocalFiles(): List<T> {
        return scan(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI) + scanCustomFolders(curFolders)
    }

    private fun scanCustomFolders(folders: Set<String>): List<T>
    {
        val result = mutableListOf<T>()
        for(folder in folders) {
            val folderTree = DocumentFile.fromTreeUri(context, Uri.parse(folder)) ?: continue
            result += searchFolder(folderTree)
        }
        return result
    }

    private fun searchFolder(folder: DocumentFile): List<T>
    {
        val result = mutableListOf<T>()
        for(file in folder.listFiles())
        {
            if(file.isDirectory) {
                searchFolder(file)
                result += scan(file.uri)
            }
        }
        return result
    }
}