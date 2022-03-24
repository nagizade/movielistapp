package com.hasannagizade.movielistapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hasannagizade.movielistapp.data.model.MovieLocalItem

@Database(
    entities = [MovieLocalItem::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverters::class)
abstract class WatchlistDatabase : RoomDatabase() {
    abstract fun watchlistDao(): WatchlistDao
}