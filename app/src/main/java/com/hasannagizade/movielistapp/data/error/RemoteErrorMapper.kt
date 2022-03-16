package com.hasannagizade.movielistapp.data.error

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class RemoteErrorMapper : ErrorMapper {

    override fun mapError(e: Throwable): Throwable = when (e) {
        is HttpException -> mapHttpErrors(e)
        is SocketException,
        is SocketTimeoutException,
        is UnknownHostException,
        -> NetworkError(e)
        else -> UnknownError(e)
    }

    private fun mapHttpErrors(error: HttpException): Throwable {
        val description = try {
            error
                .response()
                ?.errorBody()
                ?.string()
                ?.let {
                    //Timber.e(it)
                    Json {
                        ignoreUnknownKeys = true
                    }.decodeFromString<ServerProblemDescription>(it)
                }

        } catch (ex: Throwable) {
            //Timber.e(ex)
            null
        } ?: ServerProblemDescription()

        return when (error.code()) {
            in 500..600 -> {
                ServerError.ServerIsDown(description.status, description.detail)
            }
            else -> {
                ServerError.Unexpected(description.status, description.detail)
            }
        }
    }
}