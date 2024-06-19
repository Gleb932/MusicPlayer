package com.example.musicplayer.domain.usecases

import android.graphics.Bitmap
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.SongRepository
import javax.inject.Inject

class GetSongCoverUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    operator fun invoke(song: Song, size: Pair<Int, Int>): Bitmap? {
        return songRepository.getThumbnail(song, size)
    }
}