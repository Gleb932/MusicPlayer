package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.repositories.SettingsRepository
import javax.inject.Inject

class RemoveMusicFolderUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(folderUri: String) {
        settingsRepository.removeFolder(folderUri)
    }
}