package com.example.musicplayer.data

import java.util.UUID

class Mapper {
    private val roomMap: MutableMap<UUID, Long> = mutableMapOf()
    private val mediaStoreMap: MutableMap<UUID, String> = mutableMapOf()
    private val sourceMap: MutableMap<UUID, DataSource> = mutableMapOf()

    fun register(id: UUID, dataSource: DataSource, dataId: Any) {
        sourceMap[id] = dataSource
        when(dataSource) {
            DataSource.ROOM -> roomMap[id] = dataId as Long
            DataSource.MEDIA_STORE -> mediaStoreMap[id] = dataId as String
        }
    }

    fun remove(id: UUID) {
        when(sourceMap[id]) {
            DataSource.ROOM -> roomMap.remove(id)
            DataSource.MEDIA_STORE -> mediaStoreMap.remove(id)
            null -> return
        }
    }

    fun getSource(id: UUID): DataSource? {
        return sourceMap[id]
    }

    fun getRoomId(id: UUID): Long? {
        return roomMap[id]
    }

    fun getMediaStoreId(id: UUID): String? {
        return mediaStoreMap[id]
    }
}