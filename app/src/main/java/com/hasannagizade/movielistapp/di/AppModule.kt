package com.hasannagizade.movielistapp.di

import com.hasannagizade.movielistapp.presentation.tabs.toprated.TopRatedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.KoinContextHandler.get
import org.koin.dsl.module

val appModule = module {
    //////////////// View Models //////////////////////

    viewModel {
        TopRatedViewModel(
            getTopRatedUseCase = get(),
            movieRepository = get()
        )
    }


}