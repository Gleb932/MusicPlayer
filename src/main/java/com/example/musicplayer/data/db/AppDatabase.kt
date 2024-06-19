package com.example.musicplayer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [
    SongEntry::class,
    SongArtistCrossRef::class,
    SongGenreCrossRef::class,
    AlbumEntry::class,
    AlbumMakerEntry::class,
    AlbumSongCrossRef::class,
    ArtistEntry::class,
    PlaylistEntry::class,
    PlaylistSongCrossRef::class,
    RoleEntry::class,
    TagEntry::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}