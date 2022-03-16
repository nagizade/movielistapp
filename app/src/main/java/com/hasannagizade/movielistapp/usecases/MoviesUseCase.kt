package com.hasannagizade.movielistapp.usecases

import com.hasannagizade.movielistapp.data.api.MovieRepository
import com.hasannagizade.movielistapp.data.error.ErrorConverter
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.PaginationData
import kotlin.coroutines.CoroutineContext

class MoviesUseCase {

    class GetTrending(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<Nothing, PaginationData<MovieItem>>(context, errorMapper) {

        override suspend fun executeOnBackground(params: Nothing): PaginationData<MovieItem> {
            return movieRepository.getTrending()
        }
    }

    class GetUpcoming(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<Nothing, PaginationData<MovieItem>>(context, errorMapper) {

        override suspend fun executeOnBackground(params: Nothing): PaginationData<MovieItem> {
            return movieRepository.getUpcoming()
        }
    }
}