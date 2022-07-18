package com.wajid.nytimespopular.di

import com.wajid.nytimespopular.services.NYTimesPopularService
import retrofit2.Retrofit

fun provideNYTimesPopularService(retrofit: Retrofit): NYTimesPopularService =
    retrofit.create(NYTimesPopularService::class.java)