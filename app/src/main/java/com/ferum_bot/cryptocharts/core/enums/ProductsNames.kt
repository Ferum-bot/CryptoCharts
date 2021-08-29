package com.ferum_bot.cryptocharts.core.enums

enum class ProductsNames(val availableCurrency: List<Currency>) {
    BTC(Currency.getAllCurrency(),),
    ETH(Currency.getAllCurrency(),),
    MASK(Currency.getAllCurrency(),),
    SUSHI(Currency.getAllCurrency(),);

    companion object {

        fun getAllProducts(): List<ProductsNames> {
            return listOf(BTC, ETH, MASK, SUSHI)
        }
    }
}