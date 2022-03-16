package com.hasannagizade.movielistapp.data.error

class ErrorConverterImpl(private val mappers: Set<ErrorMapper>) : ErrorConverter {
    override fun convert(t: Throwable): Throwable {
        if (t is HandledException) return t

        mappers.forEach {
            val error = it.mapError(t)
            if (error is HandledException) return error
        }

        return t
    }
}