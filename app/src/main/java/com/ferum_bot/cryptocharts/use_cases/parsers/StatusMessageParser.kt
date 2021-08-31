package com.ferum_bot.cryptocharts.use_cases.parsers

import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus

private typealias Created = ApiMessage.StatusMessage.Created
private typealias Connecting = ApiMessage.StatusMessage.Connecting
private typealias Open = ApiMessage.StatusMessage.Open
private typealias Closing = ApiMessage.StatusMessage.Closing
private typealias Closed = ApiMessage.StatusMessage.Closed

class StatusMessageParser: InComingMessagesParser<ApiMessage.StatusMessage, SocketConnectionStatus> {

    override fun parse(message: ApiMessage.StatusMessage): SocketConnectionStatus {
        return when(message) {
            is Created, is Connecting -> SocketConnectionStatus.CONNECTING
            is Closing, is Closed -> SocketConnectionStatus.DISCONNECTED
            is Open -> SocketConnectionStatus.CONNECTED
        }
    }

}