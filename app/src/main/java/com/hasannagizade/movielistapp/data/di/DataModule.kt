package com.hasannagizade.movielistapp.data.di

import com.hasannagizade.movielistapp.data.api.MovieRepository
import com.hasannagizade.movielistapp.data.api.MovieRepositoryImpl
import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSource
import com.hasannagizade.movielistapp.data.api.remote.MovieRemoteDataSourceImpl
import com.hasannagizade.movielistapp.data.error.ErrorConverter
import com.hasannagizade.movielistapp.data.error.ErrorConverterImpl
import com.hasannagizade.movielistapp.data.error.ErrorMapper
import com.hasannagizade.movielistapp.data.error.RemoteErrorMapper
import com.hasannagizade.movielistapp.data.interceptors.TokenInterceptor
import com.hasannagizade.movielistapp.data.interceptors.TokenInterceptorImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"


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
            if (getProperty("isDebug") == true.toString()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
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

    // -------------- Data source -----------------

    factory<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(api = get()) }

    // Repository
    factory<MovieRepository> {
        MovieRepositoryImpl(
            remoteDataSource = get()
        )
    }

}