package com.hasannagizade.movielistapp.data.error

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}