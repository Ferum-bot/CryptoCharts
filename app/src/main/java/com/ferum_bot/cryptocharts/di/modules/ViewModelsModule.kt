package com.ferum_bot.cryptocharts.di.modules

import androidx.lifecycle.ViewModel
import com.ferum_bot.cryptocharts.core.annotations.ViewModelKey
import com.ferum_bot.cryptocharts.ui.ChartsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel::class)
    abstract fun bindViewModel(viewModel: ChartsViewModel): ViewModel

}