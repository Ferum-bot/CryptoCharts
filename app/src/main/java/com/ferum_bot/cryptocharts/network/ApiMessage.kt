package com.ferum_bot.cryptocharts.network

@Suppress("ArrayInDataClass")
sealed class ApiMessage() {

    sealed class ErrorMessage(open val exception: Exception): ApiMessage() {

        data class ConnectError(
            override val exception: Exception
        ): ErrorMessage(exception)

        data class SocketError(
            override val exception: Exception
        ): ErrorMessage(exception)
    }

    sealed class StatusMessage: ApiMessage() {

        object Created: StatusMessage()

        object Connecting: StatusMessage()

        object Open: StatusMessage()

        object Closing: StatusMessage()

        object Closed: StatusMessage()
    }

    sealed class MessageReceived: ApiMessage() {

        data class TextMessageReceived(val text: String): MessageReceived()

        data class BinaryMessageReceived(val bytes: ByteArray): MessageReceived()
    }


}
