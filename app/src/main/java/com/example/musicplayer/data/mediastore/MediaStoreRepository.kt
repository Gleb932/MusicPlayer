package com.example.musicplayer.data.mediastore

import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.repositories.BaseDataRepository
import com.example.musicplayer.domain.Entity
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaStoreRepository<T : Entity> @Inject constructor(): BaseDataRepository<T, Long>() {
    override val dataSource: DataSource
        get() = DataSource.MEDIA_STORE

    override fun store(entity: T) {}
    override fun delete(entityId: UUID) {}
    override fun update(entity: T) {}
}