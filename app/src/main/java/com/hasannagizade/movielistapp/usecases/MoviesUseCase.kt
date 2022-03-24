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
            return movieRepository.getTrending(params.page)
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

    class GetWatchlist(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<Unit, List<MovieItem>>(context, errorMapper) {

        override suspend fun executeOnBackground(params: Unit): List<MovieItem> {
            return movieRepository.getWatchlist()
        }
    }

    class AddToWatchlist(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<AddToWatchlist.Params,Unit>(context, errorMapper) {

        class Params(
            val movie: MovieItem
        )

        override suspend fun executeOnBackground(params: Params) {
            return movieRepository.addWatchlist(params.movie)
        }
    }

    class GetMovie(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<GetMovie.Params,MovieItem?>(context, errorMapper) {

        class Params(
            val movieId: Int
        )

        override suspend fun executeOnBackground(params: Params) : MovieItem? {
            return movieRepository.getMovie(params.movieId)
        }
    }

    class RemoveFromWatchlist(
        context: CoroutineContext,
        errorMapper: ErrorConverter,
        private val movieRepository: MovieRepository
    ) : BaseUseCase<RemoveFromWatchlist.Params,Unit>(context, errorMapper) {

        class Params(
            val movieId: Int
        )

        override suspend fun executeOnBackground(params: Params) {
            return movieRepository.removeWatchlist(params.movieId)
        }
    }


}