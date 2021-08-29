package com.ferum_bot.cryptocharts.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ferum_bot.cryptocharts.core.extensions.layoutInflater
import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.databinding.LayoutTickerBinding

@Suppress("JoinDeclarationAndAssignment")
class TickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    var ticker: Ticker? = null
    set(value) {
        field = value
        applyNewTicker(value ?: return)
    }

    private val binding: LayoutTickerBinding

    init {
        binding = LayoutTickerBinding.inflate(layoutInflater, this, true)
    }

    private fun applyNewTicker(ticker: Ticker) = with(binding) {
        productNameValue.text = ticker.productName
        productPriceValue.text = ticker.currentPrice
        openPriceValue.text = ticker.openDayPrice
        volumePriceValue.text = ticker.volumeDayPrice
        lowPriceValue.text = ticker.lowDayPrice
        highPriceValue.text = ticker.highDayPrice
        bestBidValue.text = ticker.bestBid
        bestAskValue.text = ticker.bestAsk
        timeValue.text = ticker.time
        sideValue.text = ticker.side
    }
}