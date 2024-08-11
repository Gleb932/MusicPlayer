package com.example.musicplayer.data.repositories

import com.example.musicplayer.data.CompositeIdMapper
import com.example.musicplayer.data.LocalFilesSource
import com.example.musicplayer.domain.Entity
import com.example.musicplayer.domain.repositories.Repository

interface CompositeRepository<T : Entity>: Repository<T>, CompositeIdMapper, LocalFilesSource