package com.rumatu.mvicompose

import android.app.Application
import com.rumatu.api.di.apiModule
import com.rumatu.mvicompose.di.viewModelModule
import com.rumatu.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    apiModule,
                    viewModelModule
                )
            )
        }
    }
}
