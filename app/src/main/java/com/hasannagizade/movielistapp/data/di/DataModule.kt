package com.hasannagizade.movielistapp.data.di

import androidx.room.Room
import com.hasannagizade.movielistapp.data.api.MovieRepository
import com.hasannagizade.movielistapp.data.api.MovieRepositoryImpl
import com.hasannagizade.movielistapp.data.api.remote.MovieAPI
import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSource
import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSourceImpl
import com.hasannagizade.movielistapp.data.error.ErrorConverter
import com.hasannagizade.movielistapp.data.error.ErrorConverterImpl
import com.hasannagizade.movielistapp.data.error.ErrorMapper
import com.hasannagizade.movielistapp.data.error.RemoteErrorMapper
import com.hasannagizade.movielistapp.data.interceptors.TokenInterceptor
import com.hasannagizade.movielistapp.data.interceptors.TokenInterceptorImpl
import com.hasannagizade.movielistapp.data.local.MoviesLocalDataSource
import com.hasannagizade.movielistapp.data.local.MoviesLocalDataSourceImpl
import com.hasannagizade.movielistapp.data.local.WatchlistDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"


@ExperimentalSerializationApi
val dataModule = module {
    single<ErrorConverter> {
        ErrorConverterImpl(
            setOf(
                get(named(ERROR_MAPPER_NETWORK))
            )
        )
    }

    factory<ErrorMapper>(named(ERROR_MAPPER_NETWORK)) { RemoteErrorMapper() }

    // -------------- Parser -----------------

    factory {
        Json {
            isLenient = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    // -------------- Network -----------------

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<TokenInterceptor>())
            .retryOnConnectionFailure(false)
            .build()
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    single<TokenInterceptor> {
        TokenInterceptorImpl()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty("host"))
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
    }

    ////////////////// DATABASE ////////////////////////

    single {
        Room.databaseBuilder(
            androidContext(),
            WatchlistDatabase::class.java,
            "watchlist-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<WatchlistDatabase>().watchlistDao() }


    // -------------- Data source -----------------

    factory<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(api = get()) }

    single<MoviesLocalDataSource> { MoviesLocalDataSourceImpl(watchlistDao = get()) }

    // Repository
    factory<MovieRepository> {
        MovieRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    /////////////////////// API ////////////////////////
    factory<MovieAPI> { get<Retrofit>().create(MovieAPI::class.java) }


}