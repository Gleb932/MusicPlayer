package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicFoldersUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return settingsRepository.folders
    }
}