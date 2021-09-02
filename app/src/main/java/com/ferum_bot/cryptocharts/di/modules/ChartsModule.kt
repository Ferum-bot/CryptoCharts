package com.ferum_bot.cryptocharts.di.modules

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.data_sources.SocketConnectionDataSource
import com.ferum_bot.cryptocharts.data_sources.impl.DefaultSocketDataSource
import com.ferum_bot.cryptocharts.di.DIConstants
import com.ferum_bot.cryptocharts.di.ExceptionParser
import com.ferum_bot.cryptocharts.di.StatusParser
import com.ferum_bot.cryptocharts.di.TickerParser
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.interactors.impl.DefaultChartsInteractor
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.repositories.ChartsRepository
import com.ferum_bot.cryptocharts.repositories.impl.DefaultChartsRepository
import com.ferum_bot.cryptocharts.use_cases.adapters.DefaultTickerSizeAdapter
import com.ferum_bot.cryptocharts.use_cases.adapters.TickerSizeAdapter
import com.ferum_bot.cryptocharts.use_cases.parsers.InComingMessagesParser
import dagger.Module
import dagger.Provides
import java.net.URI
import javax.net.ssl.SSLSocketFactory

@Module
class ChartsModule {

    @Provides
    fun provideInteractor(repository: ChartsRepository): ChartsInteractor {
        return DefaultChartsInteractor(repository)
    }

    @Provides
    fun provideTickerSizeAdapter(): TickerSizeAdapter {
        return DefaultTickerSizeAdapter(DIConstants.MAX_TICKER_SIZE, DIConstants.KEEP_TICKERS_UNIQUE)
    }

    @Provides
    fun provideSocketDataSource(): SocketConnectionDataSource {
        val apiUri = URI("wss://ws-feed.pro.coinbase.com")
        val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        return DefaultSocketDataSource(apiUri, socketFactory)
    }

    @Provides
    fun provideChartsRepository(
        @TickerParser
        tickerParser: InComingMessagesParser<ApiMessage.MessageReceived, Ticker>,

        @StatusParser
        statusParser: InComingMessagesParser<ApiMessage.StatusMessage, SocketConnectionStatus>,

        @ExceptionParser
        exceptionParser: InComingMessagesParser<ApiMessage.ErrorMessage, Exception>,

        dataSource: SocketConnectionDataSource,
    ): ChartsRepository {
        return DefaultChartsRepository(dataSource, tickerParser, exceptionParser, statusParser)
    }
}