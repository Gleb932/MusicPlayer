package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.SongRepository
import javax.inject.Inject

class GetLocalSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(): List<Song> {
        return songRepository.getLocalSongs()
    }
}