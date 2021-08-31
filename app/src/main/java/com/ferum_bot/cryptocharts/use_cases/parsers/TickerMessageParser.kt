package com.ferum_bot.cryptocharts.use_cases.parsers

import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.squareup.moshi.Moshi

class TickerMessageParser: InComingMessagesParser<ApiMessage.MessageReceived, Ticker> {

    override fun parse(message: ApiMessage.MessageReceived): Ticker? {
        if (message !is ApiMessage.MessageReceived.TextMessageReceived) {
            return null
        }
        val text = message.text
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Ticker::class.java)

        return adapter.fromJson(text)
    }

}