package com.ferum_bot.cryptocharts.di

import com.ferum_bot.cryptocharts.application.CryptoApplication
import com.ferum_bot.cryptocharts.di.components.AppComponent
import com.ferum_bot.cryptocharts.di.components.DaggerAppComponent

object Injector {

    private var _appComponent: AppComponent? = null

    fun initComponents(app: CryptoApplication) {
        if (_appComponent != null) {
            return
        }

        _appComponent = DaggerAppComponent.builder()
            .application(app)
            .build()
    }

    val appComponent: AppComponent
    get() = requireNotNull(_appComponent) { "Component is not initialized!" }
}