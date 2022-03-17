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
    ) : BaseUseCase<GetTrending.Params, PaginationData<MovieItem>>(context, errorMapper) {

        class Params(
            val page: Int
        )

        override suspend fun executeOnBackground(params: Params): PaginationData<MovieItem> {
            val result = movieRepository.getTrending(params.page)
            return result
        }
    }

    class GetUpcoming(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<GetUpcoming.Params, PaginationData<MovieItem>>(context, errorMapper) {

        class Params(
            val page: Int
        )

        override suspend fun executeOnBackground(params: Params): PaginationData<MovieItem> {
            return movieRepository.getUpcoming(params.page)
        }
    }
}