package com.example.musicplayer.data

import com.example.musicplayer.data.mediastore.AlbumMediaStoreDAO
import com.example.musicplayer.data.mediastore.SongMediaStoreDAO
import com.example.musicplayer.data.repositories.AlbumDataRepository
import com.example.musicplayer.data.repositories.ArtistDataRepository
import com.example.musicplayer.data.repositories.SongDataRepository
import com.example.musicplayer.domain.Maker
import com.example.musicplayer.domain.MakerRole
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFilesScanner @Inject constructor(
    private val songRepository: SongDataRepository,
    private val albumRepository: AlbumDataRepository,
    private val artistRepository: ArtistDataRepository,
    private val songMediaStoreDAO: SongMediaStoreDAO,
    private val albumMediaStoreDAO: AlbumMediaStoreDAO
) {
    fun scanLocalFiles() {
        artistRepository.scanLocalFiles()
        albumRepository.scanLocalFiles()
        songRepository.scanLocalFiles()
        linkAlbumsToArtists()
        linkSongsToAlbums()
    }

    private fun linkAlbumsToArtists() {
        for(artist in artistRepository.getLocalArtists().value) {
            val artistDataId = artistRepository.getDataId(DataSource.MEDIA_STORE, artist.id) as? Long ?: continue
            val albumMediaStoreEntries = albumMediaStoreDAO.getByArtistId(artistDataId)
            for(albumEntry in albumMediaStoreEntries){
                val albumId = albumRepository.getEntityId(DataSource.MEDIA_STORE, albumEntry.id) ?: continue
                albumRepository.getEntity(albumId)?.makers = listOf(Maker(UUID.randomUUID(), artist.id, MakerRole.Undefined))
            }
        }
    }

    private fun linkSongsToAlbums() {
        for(album in albumRepository.getLocalAlbums().value) {
            val albumDataId = albumRepository.getDataId(DataSource.MEDIA_STORE, album.id) as? Long ?: continue
            val songMediaStoreEntries = songMediaStoreDAO.getByAlbumId(albumDataId)
            for(songEntry in songMediaStoreEntries){
                val songId = songRepository.getEntityId(DataSource.MEDIA_STORE, songEntry.id) ?: continue
                val song = songRepository.getEntity(songId) ?: continue
                song.albumId = album.id
                song.makers = album.makers
            }
        }
    }
}