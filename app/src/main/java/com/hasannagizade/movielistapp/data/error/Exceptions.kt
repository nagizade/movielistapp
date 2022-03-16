package com.hasannagizade.movielistapp.data.error

import java.io.IOException

abstract class HandledException(
    cause: Throwable? = null,
    message: String? = null
) : IOException(message, cause)

class UnknownError(cause: Throwable?) : HandledException(cause)

class NetworkError(cause: Throwable?) : HandledException(cause)

sealed class UserError : HandledException() {

    object NotMatchingPartnerException : UserError()
    object NotSelectedPaymentMethod : UserError()

}

sealed class ServerError(
    open val serverCode: Int,
    open val serverMessage: String
) : HandledException() {

    data class Unexpected(
        override val serverCode: Int,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)

    data class ServerIsDown(
        override val serverCode: Int,
        override val serverMessage: String
    ) : ServerError(serverCode, serverMessage)
}