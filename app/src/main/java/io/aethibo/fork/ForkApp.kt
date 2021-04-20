package io.aethibo.fork

import android.app.Application
import io.aethibo.fork.framework.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class ForkApp : Application() {

    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules(
                appModule,
                networkModule,
                dataSourcesModule,
                repositoriesModule,
                useCasesModule,
                viewModelModule
            )
        }
    }
}