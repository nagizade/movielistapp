package com.hasannagizade.movielistapp.data.api

import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSource
import com.hasannagizade.movielistapp.data.local.MoviesLocalDataSource
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MoviesLocalDataSource
) : MovieRepository {
    override suspend fun getTrending(page: Int): PaginationData<MovieItem> {
        return remoteDataSource.getTrending(page)
    }

    override suspend fun getUpcoming(page: Int): PaginationData<MovieItem> {
        return remoteDataSource.getUpcoming(page)
    }

    override suspend fun getWatchlist(): List<MovieItem> {
        return localDataSource.getMovies()
    }

    override suspend fun addWatchlist(movieItem: MovieItem) {
        return localDataSource.addToWatchlist(movieItem)
    }

    override suspend fun removeWatchlist(movieId: Int) {
        localDataSource.removeFromWatchlist(movieId)
    }

    override suspend fun getMovie(movieId: Int): MovieItem? {
        return localDataSource.getMovie(movieId)
    }
}