package com.ferum_bot.cryptocharts.di.modules

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.di.ExceptionParser
import com.ferum_bot.cryptocharts.di.StatusParser
import com.ferum_bot.cryptocharts.di.TickerParser
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.use_cases.parsers.ErrorMessageParser
import com.ferum_bot.cryptocharts.use_cases.parsers.InComingMessagesParser
import com.ferum_bot.cryptocharts.use_cases.parsers.StatusMessageParser
import com.ferum_bot.cryptocharts.use_cases.parsers.TickerMessageParser
import dagger.Module
import dagger.Provides

@Module
class ParsersModule {

    @Provides
    @TickerParser
    fun provideTickerParser(): InComingMessagesParser<ApiMessage.MessageReceived, Ticker> {
        return TickerMessageParser()
    }

    @Provides
    @ExceptionParser
    fun provideExceptionParser(): InComingMessagesParser<ApiMessage.ErrorMessage, Exception> {
        return ErrorMessageParser()
    }

    @Provides
    @StatusParser
    fun provideStatusParser(): InComingMessagesParser<ApiMessage.StatusMessage, SocketConnectionStatus> {
        return StatusMessageParser()
    }
}