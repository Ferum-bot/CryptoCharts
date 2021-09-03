package com.ferum_bot.cryptocharts.interactors.impl

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.repositories.ChartsRepository
import com.ferum_bot.cryptocharts.use_cases.parsers.DateTimeParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import javax.inject.Inject

class DefaultChartsInteractor @Inject constructor(
    private val repository: ChartsRepository,
    private val dateTimeParser: DateTimeParser,
    private val parseDateFormat: SimpleDateFormat,
): ChartsInteractor {

    override val inComingTickers: Flow<Ticker>
    get() = repository.inComingTickers.map { ticker ->
        val date = dateTimeParser.parseFrom(ticker.time)
             ?: return@map ticker
        val parsedDate = dateTimeParser.getFrom(date, parseDateFormat)
        return@map ticker.getWithNewTime(parsedDate)
    }

    override val exceptions: Flow<Exception>
    get() = repository.exceptions

    override val currentStatus: Flow<SocketConnectionStatus>
    get() = repository.currentStatus


    override suspend fun connect() {
        repository.connect()
    }

    override suspend fun reconnect() {
        repository.reconnect()
    }

    override suspend fun disconnect() {
        repository.disconnect()
    }
}