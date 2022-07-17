package com.wajid.nytimespopular.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wajid.nytimespopular.BuildConfig
import com.wajid.nytimespopular.services.interceptors.ConnectivityInterceptor
import com.wajid.nytimespopular.services.interceptors.HeadersInterceptor
import com.wajid.nytimespopular.utils.ConnectivityUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit


fun provideRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converterFactory)
        .build()
}

fun provideCache(context: Context) : Cache {
    val cacheDir = File(context.cacheDir, "urlCache")
    val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
    return Cache(cacheDir, cacheSize)
}

fun provideOkHttpClient(cache: Cache, connectivityUtil: ConnectivityUtil): OkHttpClient {
    val clientBuilder = OkHttpClient.Builder()

    clientBuilder.addInterceptor(ConnectivityInterceptor(connectivityUtil))
    clientBuilder.addInterceptor(HeadersInterceptor())

    if (BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            Timber.d(it)
        }
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    clientBuilder.connectTimeout(30, TimeUnit.SECONDS) //30 seconds timeout
    clientBuilder.readTimeout(60, TimeUnit.SECONDS)

    //adding cache
    clientBuilder.cache(cache)

    return clientBuilder.build()
}

fun provideGson(): Gson {
    return GsonBuilder()
        //we can set configuration later as per requirement
        .create()
}

fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
    return GsonConverterFactory.create(gson)
}
