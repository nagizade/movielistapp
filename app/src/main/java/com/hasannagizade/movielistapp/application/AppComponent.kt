package com.hasannagizade.movielistapp.application

import com.hasannagizade.movielistapp.data.di.dataModule
import com.hasannagizade.movielistapp.di.appModule
import com.hasannagizade.movielistapp.usecases.di.useCasesModule

val appComponent = listOf(
    dataModule,
    appModule,
    useCasesModule
)