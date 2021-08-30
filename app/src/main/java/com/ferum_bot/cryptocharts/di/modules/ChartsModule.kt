package com.ferum_bot.cryptocharts.di.modules

import com.ferum_bot.cryptocharts.di.DIConstants
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.interactors.impl.DefaultChartsInteractor
import com.ferum_bot.cryptocharts.interactors.impl.TestChartsInteractor
import com.ferum_bot.cryptocharts.use_cases.DefaultTickerSizeAdapter
import com.ferum_bot.cryptocharts.use_cases.TickerSizeAdapter
import dagger.Module
import dagger.Provides

@Module
class ChartsModule {

    @Provides
    fun provideInteractor(): ChartsInteractor {
        return TestChartsInteractor()
    }

    @Provides
    fun provideTickerSizeAdapter(): TickerSizeAdapter {
        return DefaultTickerSizeAdapter(DIConstants.MAX_TICKER_SIZE, DIConstants.KEEP_TICKERS_UNIQUE)
    }
}