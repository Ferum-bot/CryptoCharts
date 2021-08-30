package com.ferum_bot.cryptocharts.interactors

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.neovisionaries.ws.client.WebSocketException
import kotlinx.coroutines.flow.Flow

interface ChartsInteractor {

    val inComingTickers: Flow<Ticker>

    val exceptions: Flow<Exception>

}