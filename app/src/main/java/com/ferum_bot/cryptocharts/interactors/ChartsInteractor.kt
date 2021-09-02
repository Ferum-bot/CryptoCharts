package com.ferum_bot.cryptocharts.interactors

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import kotlinx.coroutines.flow.Flow

interface ChartsInteractor {

    val inComingTickers: Flow<Ticker>

    val exceptions: Flow<Exception>

    val currentStatus: Flow<SocketConnectionStatus>

    suspend fun connect()

    suspend fun reconnect()

    suspend fun disconnect()
}