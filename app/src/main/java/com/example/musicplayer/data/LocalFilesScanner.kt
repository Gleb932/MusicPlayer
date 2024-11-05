package com.example.musicplayer.data

import com.example.musicplayer.data.mediastore.MediaStoreRepository
import com.example.musicplayer.data.mediastore.dao.AlbumMediaStoreDAO
import com.example.musicplayer.data.mediastore.dao.AlbumMediaStoreEntry
import com.example.musicplayer.data.mediastore.dao.ArtistMediaStoreDAO
import com.example.musicplayer.data.mediastore.dao.ArtistMediaStoreEntry
import com.example.musicplayer.data.mediastore.dao.SongMediaStoreDAO
import com.example.musicplayer.data.mediastore.dao.SongMediaStoreEntry
import com.example.musicplayer.data.mediastore.mappers.AlbumMapper
import com.example.musicplayer.data.mediastore.mappers.ArtistMapper
import com.example.musicplayer.data.mediastore.mappers.SongMapper
import com.example.musicplayer.domain.Album
import com.example.musicplayer.domain.Artist
import com.example.musicplayer.domain.Maker
import com.example.musicplayer.domain.MakerRole
import com.example.musicplayer.domain.Song
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalFilesScanner @Inject constructor(
    private val songRepository: MediaStoreRepository<Song>,
    private val albumRepository: MediaStoreRepository<Album>,
    private val artistRepository: MediaStoreRepository<Artist>,
    private val songMediaStoreDAO: SongMediaStoreDAO,
    private val albumMediaStoreDAO: AlbumMediaStoreDAO,
    private val artistMediaStoreDAO: ArtistMediaStoreDAO
) {
    fun scanLocalFiles() {
        val artists = artistMediaStoreDAO.getAll().map { Pair(it, ArtistMapper.toDomain(it, UUID.randomUUID())) }
        val albums = albumMediaStoreDAO.getAll().map { Pair(it, AlbumMapper.toDomain(it, UUID.randomUUID())) }
        val songs = songMediaStoreDAO.getAll().map { Pair(it, SongMapper.toDomain(it, UUID.randomUUID())) }
        val filteredAlbums = filterFolderAlbums(artists, albums, songs)
        linkAlbumsToArtists(artists, filteredAlbums)
        linkSongsToArtists(artists, songs)
        linkSongsToAlbums(filteredAlbums, songs)
        for(artist in artists) {
            artistRepository.store(artist.second, artist.first.id)
        }
        for(album in filteredAlbums) {
            albumRepository.store(album.second, album.first.id)
        }
        for(song in songs) {
            songRepository.store(song.second, song.first.id)
        }
    }

    private fun linkAlbumsToArtists(
        artists: List<Pair<ArtistMediaStoreEntry, Artist>>,
        albums: List<Pair<AlbumMediaStoreEntry, Album>>
    ) {
        for(artist in artists) {
            val artistAlbums = albums.filter { it.first.artistId == artist.first.id}
            for(album in artistAlbums){
                album.second.makers += Maker(UUID.randomUUID(), artist.second.id, MakerRole.AlbumArtist)
            }
        }
    }

    private fun linkSongsToAlbums(
        albums: List<Pair<AlbumMediaStoreEntry, Album>>,
        songs: List<Pair<SongMediaStoreEntry, Song>>,
    ) {
        for(album in albums) {
            val albumSongs = songs.filter { it.first.albumId == album.first.id}
            for(song in albumSongs){
                song.second.albumId = album.second.id
                song.second.makers += album.second.makers
            }
        }
    }

    private fun linkSongsToArtists(
        artists: List<Pair<ArtistMediaStoreEntry, Artist>>,
        songs: List<Pair<SongMediaStoreEntry, Song>>
    ) {
        for(artist in artists) {
            val artistSongs = songs.filter { it.first.artistId == artist.first.id}
            for(song in artistSongs){
                song.second.makers += Maker(UUID.randomUUID(), artist.second.id, MakerRole.SongArtist)
            }
        }
    }

    private fun filterFolderAlbums(
        artists: List<Pair<ArtistMediaStoreEntry, Artist>>,
        albums: List<Pair<AlbumMediaStoreEntry, Album>>,
        songs: List<Pair<SongMediaStoreEntry, Song>>
    ): List<Pair<AlbumMediaStoreEntry, Album>> {
        return albums.filter { album ->
            songs.filter { song ->
                song.first.albumId == album.first.id
            }.none { song ->
                val name = artists.find {
                    it.first.id == song.first.artistId
                }?.first?.name ?: return@none false
                name.contains("unknown artist", true) || name.contains("<unknown>", true)
            }
        }
    }
}