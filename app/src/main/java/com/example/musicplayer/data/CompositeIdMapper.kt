package com.example.musicplayer.data

import java.util.UUID

interface CompositeIdMapper {
    fun getDataId(dataSource: DataSource, entityId: UUID): Any?
    fun getEntityId(dataSource: DataSource, dataId: Any): UUID?
}