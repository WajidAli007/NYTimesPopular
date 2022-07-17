package com.wajid.nytimespopular.services.interceptors

import okhttp3.Interceptor
import okhttp3.Response


class HeadersInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()

        builder.addHeader("Content-Type", "application/json;charset=utf-8")
        return chain.proceed(builder.build())
    }
}