package com.ferum_bot.cryptocharts.data_sources.impl

import com.ferum_bot.cryptocharts.data_sources.SocketConnectionDataSource
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.models.SubscribeRequest
import com.neovisionaries.ws.client.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@Suppress("BlockingMethodInNonBlockingContext")
class DefaultSocketDataSource @Inject constructor(
    private val socket: WebSocket,
): SocketConnectionDataSource, WebSocketAdapter() {

    companion object {

        private const val REPLAY_COUNT = 1
        private const val EXTRA_CAPACITY = 1
    }

    private val _socketMessages = MutableSharedFlow<ApiMessage>(
        replay = REPLAY_COUNT,
        extraBufferCapacity = EXTRA_CAPACITY,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override val socketMessages: SharedFlow<ApiMessage>
    get() = _socketMessages

    init {
        socket.addListener(this)
    }

    override suspend fun connect() {
        tryToConnect(socket)
    }

    override suspend fun reconnect() {
        tryToConnect(socket.recreate())
    }

    override suspend fun disconnect() {
        socket.disconnect(WebSocketCloseCode.NORMAL)
    }

    override fun onStateChanged(websocket: WebSocket?, newState: WebSocketState?) {
        newState ?: return
        val message = when(newState) {
            WebSocketState.CLOSED -> ApiMessage.StatusMessage.Closed
            WebSocketState.CLOSING -> ApiMessage.StatusMessage.Closing
            WebSocketState.CONNECTING -> ApiMessage.StatusMessage.Connecting
            WebSocketState.CREATED -> ApiMessage.StatusMessage.Created
            WebSocketState.OPEN -> ApiMessage.StatusMessage.Open.also { subscribeToApi() }
        }
        sendMessage(message)
    }

    override fun onTextMessage(websocket: WebSocket?, text: String?) {
        text ?: return
        val message = ApiMessage.MessageReceived.TextMessageReceived(text)
        sendMessage(message)
    }

    override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
        exception ?: return
        val message = ApiMessage.ErrorMessage.ConnectError(exception)
        sendMessage(message)
    }

    override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
        cause ?: return
        val message = ApiMessage.ErrorMessage.SocketError(cause)
        sendMessage(message)
    }


    override fun onFrameError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
        cause ?: return
        frame ?: return
        val message = ApiMessage.ErrorMessage.FrameError(cause, frame)
        sendMessage(message)
    }

    override fun onSendError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
        cause ?: return
        frame ?: return
        val message = ApiMessage.ErrorMessage.SendError(cause, frame)
        sendMessage(message)
    }

    override fun onTextMessageError(websocket: WebSocket?, cause: WebSocketException?, data: ByteArray?) {
        cause ?: return
        data ?: return
        val message = ApiMessage.ErrorMessage.TextMessageError(cause, data)
        sendMessage(message)
    }

    override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
        cause ?: return
        val message = ApiMessage.ErrorMessage.UnExpectedError(cause)
        sendMessage(message)
    }

    override fun onMessageError(
        websocket: WebSocket?,
        cause: WebSocketException?,
        frames: MutableList<WebSocketFrame>?
    ) {
        cause ?: return
        frames ?: return
        val message = ApiMessage.ErrorMessage.MessageError(cause, frames)
        sendMessage(message)
    }

    override fun onMessageDecompressionError(
        websocket: WebSocket?,
        cause: WebSocketException?,
        compressed: ByteArray?
    ) {
        cause ?: return
        compressed ?: return
        val message = ApiMessage.ErrorMessage.MessageDecompressionError(cause, compressed)
        sendMessage(message)
    }

    override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
        websocket ?: return
        frame ?: return
        websocket.sendPing()
    }

    private suspend fun tryToConnect(socket: WebSocket) {
        try {
            socket.connect()
        } catch (ex: OpeningHandshakeException) {
            val error = ApiMessage.ErrorMessage.OpenHandshakeError(ex)
            _socketMessages.emit(error)
        } catch (ex: HostnameUnverifiedException) {
            val error = ApiMessage.ErrorMessage.HostnameUnverifiedError(ex)
            _socketMessages.emit(error)
        } catch (ex: WebSocketException) {
            val error = ApiMessage.ErrorMessage.ConnectError(ex)
            _socketMessages.emit(error)
        }
    }

    private fun sendMessage(message: ApiMessage) = runBlocking {
        _socketMessages.emit(message)
    }

    private fun subscribeToApi() {
        val channelsToSubscribe = listOf("ticker")
        val productsToSubscribe = listOf(
            "BTC-USD", "BTC-EUR", "ETH-USD", "ETH-EUR",
            "MASK-USD", "MASK-EUR", "SUSHI-USD", "SUSHI-EUR"
        )
        val subscribeRequest = SubscribeRequest(
            channelsToSubscribe = channelsToSubscribe,
            productsToSubscribe = productsToSubscribe
        )

        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(SubscribeRequest::class.java)
        val jsonString = adapter.toJson(subscribeRequest)

        socket.sendText(jsonString)
    }
}