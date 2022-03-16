package com.hasannagizade.movielistapp.data.api.remote

import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData
import retrofit2.http.*

interface MovieAPI {

    @GET("/trending/movie/week")
    suspend fun getTrendingMovies(): PaginationData<MovieItem>

    @GET("/movie/upcoming")
    suspend fun getUpcomingMovies(): PaginationData<MovieItem>

}