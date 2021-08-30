package com.ferum_bot.cryptocharts.network.models

import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFrame

private typealias Frame = WebSocketFrame
private typealias SocketException = WebSocketException

@Suppress("ArrayInDataClass")
sealed class ApiMessage {

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
    }

    sealed class MessageReceived {

        data class TextMessageReceived(val text: String): MessageReceived()

        data class BinaryMessageReceived(val bytes: ByteArray): MessageReceived()
    }

}
