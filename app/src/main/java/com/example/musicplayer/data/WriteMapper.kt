package com.example.musicplayer.data

interface WriteMapper<Domain, Data, DataId> {
    fun toData(domain: Domain, dataId: DataId): Data
}