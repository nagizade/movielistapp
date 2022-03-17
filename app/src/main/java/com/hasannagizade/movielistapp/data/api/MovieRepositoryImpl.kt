package com.hasannagizade.movielistapp.data.api

import android.util.Log
import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSource
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override suspend fun getTrending(page: Int): PaginationData<MovieItem> {
        return remoteDataSource.getTrending(page)
    }

    override suspend fun getUpcoming(): PaginationData<MovieItem> {
        return remoteDataSource.getUpcoming()
    }
}