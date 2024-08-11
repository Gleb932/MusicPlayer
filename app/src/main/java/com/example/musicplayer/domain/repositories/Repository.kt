package com.example.musicplayer.domain.repositories

import com.example.musicplayer.domain.Entity
import java.util.UUID

interface Repository<T : Entity> {
    fun getEntity(entityId: UUID): T?
    fun getAll(): List<T>
    fun update(entity: T)
    fun store(entity: T)
    fun remove(entityId: UUID)
    fun delete(entityId: UUID)
    fun clear()
}