package com.hasannagizade.movielistapp.data.api.remote

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData

interface MovieRemoteDataSource {
    suspend fun getTrending(page: Int): PaginationData<MovieItem>
    suspend fun getUpcoming(): PaginationData<MovieItem>
}