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
        get() = TODO("Not yet implemented")

    override val exceptions: Flow<Exception>
        get() = TODO("Not yet implemented")

    override val currentStatus: Flow<SocketConnectionStatus>
        get() = TODO("Not yet implemented")
}