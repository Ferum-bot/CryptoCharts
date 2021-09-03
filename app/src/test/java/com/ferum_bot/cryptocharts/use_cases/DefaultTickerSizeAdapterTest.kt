package com.ferum_bot.cryptocharts.use_cases

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.use_cases.adapters.DefaultTickerSizeAdapter
import com.ferum_bot.cryptocharts.use_cases.adapters.TickerSizeAdapter
import org.junit.Test

import org.junit.Assert.*

class DefaultTickerSizeAdapterTest {

    @Test
    fun testAdaptLatTickers_easyCase() {
        val adapter: TickerSizeAdapter = DefaultTickerSizeAdapter(
            maxSize = 6, keepUnique = true,
        )
        val inputList = listOf(
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test3"),
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test6"),
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test9"),
        )

        val actualResult = adapter.adaptLastTickers(inputList).toTypedArray()
        val expectedResult = listOf(
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test3"),
            Ticker(productName = "test1"), Ticker(productName = "test6"), Ticker(productName = "test9"),
        ).toTypedArray()

        assertArrayEquals(expectedResult, actualResult)
    }

    @Test
    fun testAdaptLatTickers_middleCase() {
        val adapter: TickerSizeAdapter = DefaultTickerSizeAdapter(
            maxSize = 6, keepUnique = true,
        )
        val inputList = listOf(
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test3"),
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test6"),
            Ticker(productName = "test1"), Ticker(productName = "test9"), Ticker(productName = "test9"),
        )

        val actualResult = adapter.adaptLastTickers(inputList).toTypedArray()
        val expectedResult = listOf(
            Ticker(productName = "test1"), Ticker(productName = "test2"), Ticker(productName = "test3"),
            Ticker(productName = "test1"), Ticker(productName = "test6"), Ticker(productName = "test9"),
        ).toTypedArray()

        assertArrayEquals(expectedResult, actualResult)
    }
}