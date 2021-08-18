package com.ferum_bot.cryptocharts.di.components

import com.ferum_bot.cryptocharts.application.CryptoApplication
import com.ferum_bot.cryptocharts.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    AppModule::class,
])
interface AppComponent {

    val chartsComponent: ChartsComponent.Builder

    fun inject(app: CryptoApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: CryptoApplication): Builder

        fun build(): AppComponent
    }

}