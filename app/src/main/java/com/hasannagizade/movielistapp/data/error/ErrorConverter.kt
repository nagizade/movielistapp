package com.hasannagizade.movielistapp.data.error

fun interface ErrorConverter {
    fun convert(t: Throwable): Throwable
}