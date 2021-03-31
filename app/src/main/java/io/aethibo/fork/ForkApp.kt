package io.aethibo.fork

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ForkApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(applicationContext)
            modules()
        }
    }
}