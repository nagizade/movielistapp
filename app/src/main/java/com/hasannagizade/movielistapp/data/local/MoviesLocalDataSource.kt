package com.hasannagizade.movielistapp.data.local

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.MovieLocalItem
import kotlinx.coroutines.flow.Flow

interface MoviesLocalDataSource {

    val movies: Flow<List<MovieLocalItem>>

    suspend fun update(address: MovieLocalItem)

    suspend fun set(addresses: List<MovieLocalItem>)

    suspend fun clear()

    suspend fun getMovies(): List<MovieItem>

    suspend fun getMovie(movieId: Int) : MovieItem?

    suspend fun addToWatchlist(movieItem: MovieItem)
    suspend fun removeFromWatchlist(movieId: Int)

}