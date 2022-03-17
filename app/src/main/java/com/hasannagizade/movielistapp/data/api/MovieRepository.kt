package com.hasannagizade.movielistapp.data.api

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData

interface MovieRepository {
    suspend fun getTrending(page: Int) : PaginationData<MovieItem>

    suspend fun getUpcoming(page: Int): PaginationData<MovieItem>
}