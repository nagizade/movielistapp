package com.hasannagizade.movielistapp.data.local

import androidx.room.*
import com.hasannagizade.movielistapp.data.model.MovieLocalItem
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {

    @Query("SELECT * FROM watchlist_table")
    fun getMovies(): List<MovieLocalItem>

    @Query("SELECT * FROM watchlist_table")
    fun getMoviesFlow(): Flow<List<MovieLocalItem>>

    @Query("SELECT * from watchlist_table WHERE id=:id")
    fun getMovie(id: Int): MovieLocalItem?

    @Update
    fun update(vararg address: MovieLocalItem)

    @Query("DELETE FROM watchlist_table where id=:id")
    fun delete(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg address: MovieLocalItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addList(addresses: List<MovieLocalItem>)

    @Query("DELETE FROM watchlist_table")
    fun clear()
}