package com.wajid.nytimespopular.services.interceptors

import com.wajid.nytimespopular.services.exceptions.NoInternetException
import com.wajid.nytimespopular.utils.ConnectivityUtil
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptor(private val connectivityUtil: ConnectivityUtil) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!connectivityUtil.isConnectedToInternet()) {
            throw NoInternetException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}