package com.wajid.nytimespopular.di

import com.wajid.nytimespopular.services.NYTimesPopularService
import retrofit2.Retrofit

fun provideGithubSearchService(retrofit: Retrofit): NYTimesPopularService =
    retrofit.create(NYTimesPopularService::class.java)