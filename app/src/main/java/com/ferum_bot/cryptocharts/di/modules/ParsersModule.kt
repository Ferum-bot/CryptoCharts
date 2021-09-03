package com.ferum_bot.cryptocharts.di.modules

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.di.ExceptionParser
import com.ferum_bot.cryptocharts.di.ParsedDateTimePattern
import com.ferum_bot.cryptocharts.di.StatusParser
import com.ferum_bot.cryptocharts.di.TickerParser
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.use_cases.parsers.*
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideApiDateParser(): DateTimeParser {
        return ApiDateTimeParser()
    }

    @Provides
    fun provideParsedDateFormat(
        @ParsedDateTimePattern
        parsedDateTimePattern: String,
    ): SimpleDateFormat {
        return SimpleDateFormat(parsedDateTimePattern, Locale.US).apply {
            timeZone = TimeZone.getDefault()
        }
    }

    @Provides
    @ParsedDateTimePattern
    fun provideParsedDatePattern(): String {
        return "dd.MM HH:mm:ss"
    }
}