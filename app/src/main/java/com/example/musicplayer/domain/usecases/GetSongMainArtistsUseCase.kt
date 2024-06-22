package com.example.musicplayer.domain.usecases

import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.Song
import com.example.musicplayer.domain.repositories.ArtistRepository
import javax.inject.Inject

class GetSongMainArtistsUseCase @Inject constructor(
    private val artistRepository: ArtistRepository
) {
    operator fun invoke(song: Song): List<Artist> {
        return artistRepository.getSongArtists(song)
    }
}