package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.DataSource
import com.example.musicplayer.domain.Entity
import java.util.UUID

open class BaseCompositeRepository<T : Entity>(
    protected val repositories: List<DataRepository<T, Any>>
): CompositeRepository<T> {
    override fun getEntity(entityId: UUID): T? {
        for(repository in repositories){
            repository.getEntity(entityId)?.let { return it }
        }
        return null
    }

    override fun getAll(): List<T> {
        val res = mutableListOf<T>()
        for(repository in repositories){
            res += repository.getAll()
        }
        return res
    }

    override fun remove(entityId: UUID) {
        for(repository in repositories){
            repository.remove(entityId)
        }
    }

    override fun delete(entityId: UUID) {
        for(repository in repositories){
            repository.delete(entityId)
        }
    }

    override fun clear() {
        for(repository in repositories){
            repository.clear()
        }
    }

    override fun store(entity: T) {
        for(repository in repositories){
            if(repository.dataSource == DataSource.ROOM) {
                repository.store(entity)
                break
            }
        }
    }

    override fun update(entity: T) {
        for(repository in repositories){
            if(repository.dataSource == DataSource.ROOM) {
                repository.update(entity)
                break
            }
        }
    }

    override fun getDataId(dataSource: DataSource, entityId: UUID): Any? {
        for(repository in repositories){
            if(repository.dataSource == dataSource) {
                return repository.getDataId(entityId)
            }
        }
        return null
    }

    override fun getEntityId(dataSource: DataSource, dataId: Any): UUID? {
        for(repository in repositories){
            if(repository.dataSource == dataSource) {
                return when(dataSource) {
                    DataSource.ROOM -> repository.getEntityId(dataId as Long)
                    DataSource.MEDIA_STORE -> repository.getEntityId(dataId as Long)
                }
            }
        }
        return null
    }
}