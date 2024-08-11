package com.example.musicplayer.data.mediastore

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MediaStoreScanner<T> (
    private val folders: Flow<Set<String>>,
    @ApplicationContext private val context: Context
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

    fun getLocalFiles(contentUri: Uri, scan: (Uri, String?) -> List<T>, selection: String? = null): List<T> {
        val res = mutableListOf<T>()
        res += scan(contentUri, selection)
        for(folder in getNestedCustomFolders(curFolders)) {
            res += scan(folder, selection)
        }
        return res
    }

    private fun getNestedCustomFolders(folders: Set<String>): Set<Uri>
    {
        val nestedFolders = mutableSetOf<Uri>()
        for(folder in folders) {
            val folderTree = DocumentFile.fromTreeUri(context, Uri.parse(folder)) ?: continue
            nestedFolders += folderTree.uri
            nestedFolders += getNestedFolders(folderTree)
        }
        return nestedFolders
    }

    private fun getNestedFolders(folder: DocumentFile): Set<Uri>
    {
        val result = mutableSetOf<Uri>()
        for(file in folder.listFiles())
        {
            if(file.isDirectory) {
                result += getNestedFolders(file)
                result += folder.uri
            }
        }
        return result
    }
}