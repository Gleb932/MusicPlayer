package com.example.musicplayer.data

import java.util.UUID

interface ReadMapper<Domain, Data> {
    fun toDomain(data: Data, id: UUID): Domain
}