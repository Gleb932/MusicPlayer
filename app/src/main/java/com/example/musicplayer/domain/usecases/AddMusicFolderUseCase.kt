package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.repositories.SettingsRepository
import javax.inject.Inject

class AddMusicFolderUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(folderUri: String) {
        settingsRepository.addFolder(folderUri)
    }
}