package com.ferum_bot.cryptocharts.use_cases.adapters

import com.ferum_bot.cryptocharts.core.models.Ticker

interface TickerSizeAdapter {

    fun adaptLastTickers(tickers: List<Ticker>): List<Ticker>

}