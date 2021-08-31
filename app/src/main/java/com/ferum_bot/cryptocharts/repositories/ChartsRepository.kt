package com.ferum_bot.cryptocharts.repositories

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface ChartsRepository {

    val inComingTickers: SharedFlow<Ticker>

    val exceptions: SharedFlow<Exception>

    val currentStatus: SharedFlow<SocketConnectionStatus>

    suspend fun connect()

    suspend fun reconnect()

    suspend fun disconnect()
}