package com.ferum_bot.cryptocharts.interactors.impl

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.repositories.ChartsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultChartsInteractor @Inject constructor(
    private val repository: ChartsRepository,
): ChartsInteractor {

    override val inComingTickers: Flow<Ticker>
    get() = repository.inComingTickers

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