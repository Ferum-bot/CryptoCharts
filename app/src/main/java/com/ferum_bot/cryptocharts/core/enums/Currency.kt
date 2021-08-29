package com.ferum_bot.cryptocharts.core.enums

import kotlin.math.E

enum class Currency(val string: String) {
    USD("USD"),
    EUR("EUR");

    companion object {

        fun getAllCurrency(): List<Currency> {
            return listOf(USD, EUR)
        }
    }
}