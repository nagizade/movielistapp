package com.hasannagizade.movielistapp.data.api.remote

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.Pagination
import com.hasannagizade.movielistapp.data.model.PaginationData

class MovieRemoteDataSourceImpl(
    private val api: MovieAPI,
) : MovieRemoteDataSource {
    override suspend fun getTrending(): PaginationData<MovieItem> {
        return api.getTrendingMovies()
    }

    override suspend fun getUpcoming(): PaginationData<MovieItem> {
        return api.getUpcomingMovies()
    }
}