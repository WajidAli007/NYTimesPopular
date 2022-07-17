package com.wajid.nytimespopular

import android.app.Application
import com.wajid.nytimespopular.di.appModule
import com.wajid.nytimespopular.di.networkServicesModule
import com.wajid.nytimespopular.di.viewModelsModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //setup
        setupTimber() //for logging via Timber
        setupKoin() // for DI
    }

    /**
     * setting up Timber for logging.
     * custom trees will be added for different variants as per need.
     */
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * setup koin DI
     * android logger will be set to print logs in debug mode only
     * application context provided to koin
     * and modules will be added
     */
    private fun setupKoin() {
        startKoin {
            androidLogger(
                if (BuildConfig.DEBUG)
                    Level.INFO
                else
                    Level.NONE
            )
            androidContext(this@App)
            modules(
                appModule,
                viewModelsModules,
                networkServicesModule
            )
        }
    }
}