package com.ferum_bot.cryptocharts.use_cases.parsers

import com.ferum_bot.cryptocharts.network.ApiMessage

class ErrorMessageParser: InComingMessagesParser<ApiMessage.ErrorMessage, Exception> {

    override fun parse(message: ApiMessage.ErrorMessage): Exception {
        return Exception(message.exception)
    }

}