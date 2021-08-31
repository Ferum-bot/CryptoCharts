package com.ferum_bot.cryptocharts.ui.recycler

import android.view.ViewGroup
import com.ferum_bot.cryptocharts.R
import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.ui.custom_view.TickerView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate

object MainDelegates {

    fun tickersDelegate() = adapterDelegate<Ticker, Ticker>(
        R.layout.layout_ticker,
        layoutInflater = { parent: ViewGroup, _: Int ->
            val context = parent.context
            return@adapterDelegate TickerView.create(context)
        }
    ) {
        val tickerView = itemView as? TickerView

        bind {
            tickerView ?: return@bind
            tickerView.ticker = item
        }
    }

}