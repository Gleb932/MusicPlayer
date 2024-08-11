package com.example.musicplayer.data.repositories

import com.example.musicplayer.domain.Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

abstract class BaseDataRepository<T: Entity, DataId>: DataRepository<T, DataId> {
    private val identityMap = mutableMapOf<UUID, T>()
    private val dataMap = mutableMapOf<UUID, DataId>()
    protected val flow: MutableStateFlow<List<T>> = MutableStateFlow(listOf())

    override fun getFlow(): StateFlow<List<T>> {
        return flow
    }

    override fun getDataId(entityId: UUID): DataId? {
        return dataMap[entityId]
    }

    override fun getEntityId(dataId: Any): UUID? {
        return dataMap.entries.find { it.value == dataId }?.key
    }

    override fun getEntity(entityId: UUID): T? {
        return identityMap[entityId]
    }

    override fun getAll(): List<T> {
        return identityMap.values.distinct()
    }

    fun store(entity: T, dataId: DataId) {
        dataMap[entity.id] = dataId
        identityMap[entity.id] = entity
        flow.update {
            it + entity
        }
    }

    override fun remove(entityId: UUID) {
        val entity = identityMap.remove(entityId)
        dataMap.remove(entityId)
        if(entity != null) {
            flow.update {
                it - entity
            }
        }
    }

    override fun clear() {
        identityMap.clear()
        dataMap.clear()
    }
}