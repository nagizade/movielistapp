package com.hasannagizade.movielistapp.di

import com.hasannagizade.movielistapp.presentation.tabs.details.MovieDetailsViewModel
import com.hasannagizade.movielistapp.presentation.tabs.toprated.TopRatedViewModel
import com.hasannagizade.movielistapp.presentation.tabs.upcoming.UpcomingViewModel
import com.hasannagizade.movielistapp.presentation.tabs.watchlist.WatchlistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //////////////// View Models //////////////////////

    viewModel {
        TopRatedViewModel(
            getTopRatedUseCase = get()
        )
    }

    viewModel {
        UpcomingViewModel(
            getUpcoming = get()
        )
    }

    viewModel {
        WatchlistViewModel(
            getWatchlist = get()
        )
    }

    viewModel {
        MovieDetailsViewModel(
            addToWatchlist = get(),
            removeFromWatchlist = get(),
            getMovie = get()
        )
    }


}