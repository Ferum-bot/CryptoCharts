package com.ferum_bot.cryptocharts.repositories.impl

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.data_sources.SocketConnectionDataSource
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.repositories.ChartsRepository
import com.ferum_bot.cryptocharts.use_cases.parsers.InComingMessagesParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultChartsRepository @Inject constructor(
    private val dataSource: SocketConnectionDataSource,
    private val receivedMessageParser: InComingMessagesParser<ApiMessage.MessageReceived, Ticker>,
    private val errorMessageParser: InComingMessagesParser<ApiMessage.ErrorMessage, Exception>,
    private val statusMessageParser: InComingMessagesParser<ApiMessage.StatusMessage, SocketConnectionStatus>,
): ChartsRepository {

    private val _inComingTickers = MutableSharedFlow<Ticker>()
    override val inComingTickers: SharedFlow<Ticker>
    get() = _inComingTickers

    private val _exceptions = MutableSharedFlow<Exception>()
    override val exceptions: SharedFlow<Exception>
    get() = _exceptions

    private val _currentStatus = MutableSharedFlow<SocketConnectionStatus>()
    override val currentStatus: SharedFlow<SocketConnectionStatus>
    get() = _currentStatus

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            dataSource.socketMessages.collect { message ->
                handleInComingMessage(message)
            }
        }
    }

    override suspend fun connect() = withContext(Dispatchers.IO){
        dataSource.connect()
    }

    override suspend fun reconnect() = withContext(Dispatchers.IO) {
        dataSource.reconnect()
    }

    override suspend fun disconnect() = withContext(Dispatchers.IO) {
        dataSource.disconnect()
    }

    private suspend fun handleInComingMessage(message: ApiMessage) {
        when(message) {
            is ApiMessage.ErrorMessage -> sendException(message)
            is ApiMessage.MessageReceived -> handleReceivedMessage(message)
            is ApiMessage.StatusMessage -> handleStatusMessage(message)
        }
    }

    private suspend fun sendException(message: ApiMessage.ErrorMessage) {
        val exception = errorMessageParser.parse(message) ?: return
        _exceptions.emit(exception)
    }

    private suspend fun handleReceivedMessage(message: ApiMessage.MessageReceived) {
        val ticker = receivedMessageParser.parse(message) ?: return
        _inComingTickers.emit(ticker)
    }

    private suspend fun handleStatusMessage(message: ApiMessage.StatusMessage) {
        val status = statusMessageParser.parse(message) ?: return
        _currentStatus.emit(status)
    }
}