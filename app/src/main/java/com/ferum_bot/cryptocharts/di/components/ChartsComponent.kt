package com.ferum_bot.cryptocharts.di.components

import com.ferum_bot.cryptocharts.di.modules.ChartsModule
import com.ferum_bot.cryptocharts.ui.ChartsActivity
import com.ferum_bot.cryptocharts.ui.util.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [
    ChartsModule::class,
])
interface ChartsComponent {

    val viewModelFactory: ViewModelFactory

    fun inject(activity: ChartsActivity)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun activity(activity: ChartsActivity): Builder

        fun build(): ChartsComponent
    }
}