package com.ferum_bot.cryptocharts.application

import android.app.Application
import com.ferum_bot.cryptocharts.di.Injector

class CryptoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initDI()
    }

    private fun initDI() {
        Injector.initComponents(this)
    }
}