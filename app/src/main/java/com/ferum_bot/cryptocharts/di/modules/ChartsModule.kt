package com.ferum_bot.cryptocharts.di.modules

import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.interactors.impl.DefaultChartsInteractor
import dagger.Module
import dagger.Provides

@Module
class ChartsModule {

    @Provides
    fun provideInteractor(): ChartsInteractor {
        return DefaultChartsInteractor()
    }
}