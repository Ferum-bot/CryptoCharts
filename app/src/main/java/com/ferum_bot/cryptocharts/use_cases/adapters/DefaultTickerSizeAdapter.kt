package com.ferum_bot.cryptocharts.use_cases.adapters

import com.ferum_bot.cryptocharts.core.models.Ticker
import javax.inject.Inject

class DefaultTickerSizeAdapter @Inject constructor(
    private val maxSize: Int,
    private val keepUnique: Boolean,
): TickerSizeAdapter {

    override fun adaptLatTickers(tickers: List<Ticker>): List<Ticker> {
        if (tickers.size <= maxSize) {
            return tickers
        }

        val numberOfProducts = mutableMapOf<String, Int>()
        tickers.forEach { ticker ->
            val currentNumber = numberOfProducts.getOrPut(ticker.productName) { 0 }
            numberOfProducts[ticker.productName] = currentNumber + 1
        }

        val resultList = mutableListOf<Ticker>()
        var deletedCount = 0
        tickers.asReversed().forEach { ticker ->
            if (tickers.size - deletedCount <= maxSize) {
                resultList.add(ticker)
                return@forEach
            }

            if (keepUnique && numberOfProducts.isTickerUnique(ticker)) {
                resultList.add(ticker)
                return@forEach
            }

            deletedCount++
            val newProductCount = numberOfProducts[ticker.productName]?.minus(1)
                ?: return@forEach
            numberOfProducts[ticker.productName] = newProductCount
        }

        return resultList.asReversed()
    }

    private fun Map<String, Int>.isTickerUnique(ticker: Ticker): Boolean {
        return toList()
            .find { pair ->
                pair.first == ticker.productName && pair.second == 1
            } != null
    }
}