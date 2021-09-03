package com.ferum_bot.cryptocharts.application

import android.app.Application
import com.ferum_bot.cryptocharts.BuildConfig
import com.ferum_bot.cryptocharts.di.Injector
import timber.log.Timber

class CryptoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
        initTimber()
    }

    private fun initDI() {
        Injector.initComponents(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}