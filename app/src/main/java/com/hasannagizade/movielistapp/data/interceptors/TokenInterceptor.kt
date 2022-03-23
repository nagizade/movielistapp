package com.hasannagizade.movielistapp.data.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


interface TokenInterceptor : Interceptor {
    fun getToken(): String?
}

class TokenInterceptorImpl : TokenInterceptor {
    private var token: String? = null

    override fun getToken() = token

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", "")
            .build()

        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}