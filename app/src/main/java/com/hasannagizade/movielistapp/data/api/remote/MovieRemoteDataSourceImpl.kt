package com.hasannagizade.movielistapp.data.api.remote

import android.util.Log
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.Pagination
import com.hasannagizade.movielistapp.data.model.PaginationData

class MovieRemoteDataSourceImpl(
    private val api: MovieAPI,
) : MovieRemoteDataSource {
    override suspend fun getTrending(page: Int): PaginationData<MovieItem> {
        val result = api.getTrendingMovies(page)
        return PaginationData(
            result.results,
            result.page,
            result.total_pages
        )
    }

    override suspend fun getUpcoming(): PaginationData<MovieItem> {
        return api.getUpcomingMovies()
    }
}