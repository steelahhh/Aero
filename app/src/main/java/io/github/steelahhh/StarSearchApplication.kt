package io.github.steelahhh

import android.app.Application
import io.github.steelahhh.di.applicationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.LogcatTree
import timber.log.Timber

@Suppress("unused")
class StarSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(LogcatTree())
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@StarSearchApplication)
            modules(applicationModules)
        }
    }
}
