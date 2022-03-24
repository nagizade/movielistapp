package com.hasannagizade.movielistapp.data.local

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.MovieLocalItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesLocalDataSourceImpl(
    private val watchlistDao: WatchlistDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesLocalDataSource {

    override val movies: Flow<List<MovieLocalItem>>
        get() = watchlistDao.getMoviesFlow()

    override suspend fun update(address: MovieLocalItem) {
        withContext(ioDispatcher) {
            try {
                watchlistDao.update(address)
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun set(addresses: List<MovieLocalItem>) {
        withContext(ioDispatcher) {
            try {
                watchlistDao.clear()
                watchlistDao.addList(addresses)
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun clear() {
        withContext(ioDispatcher) {
            try {
                watchlistDao.clear()
            } catch (e: Exception) {
            }
        }
    }

    override suspend fun getMovies(): List<MovieItem> {
        return withContext(ioDispatcher) {
            try {
                watchlistDao.getMovies().map { it.toModel() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun getMovie(movieId: Int): MovieItem? {
        return withContext(ioDispatcher) {
            try {
                watchlistDao.getMovie(movieId)?.toModel()
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun addToWatchlist(movieItem: MovieItem) {
        return withContext(ioDispatcher) {
            try {
                watchlistDao.add(movieItem.toMovieLocalItem())
            } catch (e: java.lang.Exception) {
            }
        }
    }

    override suspend fun removeFromWatchlist(movieId: Int) {
        return withContext(ioDispatcher) {
            try {
                watchlistDao.delete(movieId)
            } catch (e: java.lang.Exception) {
            }
        }
    }

}