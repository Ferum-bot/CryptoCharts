package com.ferum_bot.cryptocharts.network

import com.neovisionaries.ws.client.*

private typealias Frame = WebSocketFrame
private typealias Socket = WebSocket
private typealias SocketException = WebSocketException

@Suppress("ArrayInDataClass")
sealed class ApiMessage() {

    sealed class ErrorMessage(open val exception: SocketException): ApiMessage() {

        data class ConnectError(
            override val exception: SocketException
        ): ErrorMessage(exception)

        data class SocketError(
            override val exception: SocketException
        ): ErrorMessage(exception)

        data class FrameError(
            override val exception: SocketException,
            val frame: Frame
        ): ErrorMessage(exception)

        data class MessageError(
            override val exception: SocketException,
            val frames: List<Frame>
        ): ErrorMessage(exception)

        data class MessageDecompressionError(
            override val exception: SocketException,
            val bytes: ByteArray,
        ): ErrorMessage(exception)

        data class TextMessageError(
            override val exception: SocketException,
            val data: ByteArray,
        ): ErrorMessage(exception)

        data class SendError(
            override val exception: SocketException,
            val frame: Frame,
        ): ErrorMessage(exception)

        data class UnExpectedError(
            override val exception: SocketException,
        ): ErrorMessage(exception)

        data class OpenHandshakeError(
            override val exception: OpeningHandshakeException
        ): ErrorMessage(exception)

        data class HostnameUnverifiedError(
            override val exception: HostnameUnverifiedException
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
