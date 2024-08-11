package com.example.musicplayer.data

import java.util.UUID

interface IdMapper<out DataId>{
    fun getDataId(entityId: UUID): DataId?
    fun getEntityId(dataId: Any): UUID?
}