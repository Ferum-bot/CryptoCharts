package com.ferum_bot.cryptocharts.interactors.impl

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*
import kotlin.random.Random

class TestChartsInteractor: ChartsInteractor {

    override val exceptions: Flow<Exception> = flow {  }

    override val inComingTickers: Flow<Ticker> = flow {
        delay(4000L)
        repeat(150) {
            val ticker = generateTicker()
            delay(500L)
            emit(ticker)
        }

    }

    override val currentStatus: Flow<SocketConnectionStatus> = flow {
        emit(SocketConnectionStatus.CONNECTING)
        delay(4000L)
        emit(SocketConnectionStatus.CONNECTED)
    }

    private fun generateTicker(): Ticker {
        return Ticker(
            type = "ticker",
            sequence = Random.nextLong(),
            productName = "BTC-USD",
            currentPrice = Random.nextInt().toString(),
            highDayPrice = Random.nextInt().toString(),
            lowDayPrice = Random.nextInt().toString(),
            volumeDayPrice = Random.nextInt().toString(),
            openDayPrice = Random.nextInt().toString(),
            bestAsk = Random.nextInt().toString(),
            bestBid = Random.nextInt().toString(),
            time = Date().toString(),
            side = "Buy"
        )
    }
}