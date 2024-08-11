package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.DataSource
import com.example.musicplayer.data.IdMapper
import com.example.musicplayer.domain.Entity
import com.example.musicplayer.domain.repositories.Repository
import kotlinx.coroutines.flow.StateFlow

interface DataRepository<Domain : Entity, out DataId>: Repository<Domain>, IdMapper<DataId> {
    val dataSource: DataSource
    fun getFlow(): StateFlow<List<Domain>>
}