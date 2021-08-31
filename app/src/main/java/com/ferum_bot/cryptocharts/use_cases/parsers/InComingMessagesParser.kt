package com.ferum_bot.cryptocharts.use_cases.parsers

import com.ferum_bot.cryptocharts.network.ApiMessage

interface InComingMessagesParser<IN: ApiMessage, OUT: Any> {

    fun parse(message: IN): OUT?
}