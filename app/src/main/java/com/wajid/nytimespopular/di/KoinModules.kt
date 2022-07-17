package com.wajid.nytimespopular.di

import com.wajid.nytimespopular.BuildConfig
import com.wajid.nytimespopular.repos.NYPopularRepo
import com.wajid.nytimespopular.ui.main.MainActivityViewModel
import com.wajid.nytimespopular.utils.ConnectivityUtil
import com.wajid.nytimespopular.utils.ConnectivityUtilImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * app level dependencies will be provided from this module
 * i.e. DB, Network Layer etc
 */
val appModule = module {

    //utils
    single<ConnectivityUtil> { ConnectivityUtilImpl(get()) }
    single { provideGson() }

    single(named(BASE_URL_STRING)) { BuildConfig.BASE_URL }

    //network api client/wrapper
    single { provideGsonConverterFactory(get()) }
    single { provideCache(get()) }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get(named(BASE_URL_STRING)), get(), get()) }
}

/**
 * view models will be provided by this module
 */
val viewModelsModules = module {
    viewModel { MainActivityViewModel(get()) }
}

/**
 * all network services will be provided from this module
 */
val networkServicesModule = module {
    factory { provideGithubSearchService(get()) }
}

/**
 * repos providers
 */
val reposModule = module {
    factory {
        NYPopularRepo(get())
    }
}

/**
 * named properties names
 */
const val BASE_URL_STRING = "BASE_URL_STRING"