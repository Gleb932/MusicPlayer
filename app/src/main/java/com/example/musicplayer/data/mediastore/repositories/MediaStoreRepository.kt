package com.example.musicplayer.data.mediastore.repositories

import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.LocalFilesSource
import com.example.musicplayer.data.repositories.BaseDataRepository
import com.example.musicplayer.domain.Entity
import java.util.UUID

abstract class MediaStoreRepository<T : Entity>: BaseDataRepository<T, Long>(), LocalFilesSource {
    override val dataSource: DataSource
        get() = DataSource.MEDIA_STORE

    override fun store(entity: T) {}
    override fun delete(entityId: UUID) {}
    override fun update(entity: T) {}
}