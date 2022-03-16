package com.hasannagizade.movielistapp.data.interceptors

import okhttp3.Interceptor
import okhttp3.Response

interface TokenInterceptor : Interceptor {
    fun getToken(): String?
}

class TokenInterceptorImpl : TokenInterceptor {
    private var token: String? = null

    override fun getToken() = token

    override fun intercept(chain: Interceptor.Chain): Response {

        val mRequest = chain.request()
        val originalHttpUrl = mRequest.url

        // TODO: Put your api key in here
        val httpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key","YOUR API KEY HERE")
            .build()

        val reqBuilder = mRequest.newBuilder()
            .url(httpUrl)

        val request = reqBuilder.build()

        return chain.proceed(request)
    }
}