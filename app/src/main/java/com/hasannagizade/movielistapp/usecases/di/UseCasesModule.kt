package com.hasannagizade.movielistapp.usecases.di

import com.hasannagizade.movielistapp.usecases.MoviesUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

const val IO_CONTEXT = "IO_CONTEXT"

val useCasesModule = module {
    single<CoroutineContext>(named(IO_CONTEXT)) { Dispatchers.IO }


    factory {
        MoviesUseCase.GetTrending(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }

    factory {
        MoviesUseCase.GetUpcoming(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }

    factory {
        MoviesUseCase.GetWatchlist(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }

    factory {
        MoviesUseCase.AddToWatchlist(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }

    factory {
        MoviesUseCase.RemoveFromWatchlist(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }

    factory {
        MoviesUseCase.GetMovie(
            context = get(named(IO_CONTEXT)), errorMapper = get(),
            movieRepository = get()
        )
    }
}