package com.ferum_bot.cryptocharts.data_sources

import com.ferum_bot.cryptocharts.network.ApiMessage
import kotlinx.coroutines.flow.SharedFlow

interface SocketConnectionDataSource {

    val socketMessages: SharedFlow<ApiMessage>

    suspend fun connect()

    suspend fun reconnect()

    suspend fun disconnect()

}