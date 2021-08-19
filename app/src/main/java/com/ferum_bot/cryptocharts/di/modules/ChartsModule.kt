package com.ferum_bot.cryptocharts.di.modules

import androidx.lifecycle.ViewModel
import com.ferum_bot.cryptocharts.core.annotations.ViewModelKey
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.interactors.impl.DefaultChartsInteractor
import com.ferum_bot.cryptocharts.ui.ChartsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ChartsModule {

    @Provides
    fun provideInteractor(): ChartsInteractor {
        return DefaultChartsInteractor()
    }
}