package com.ferum_bot.cryptocharts.data_sources.impl

import com.ferum_bot.cryptocharts.data_sources.SocketConnectionDataSource
import com.ferum_bot.cryptocharts.network.ApiMessage
import com.ferum_bot.cryptocharts.network.models.SubscribeRequest
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.runBlocking
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

@Suppress("BlockingMethodInNonBlockingContext")
class DefaultSocketDataSource @Inject constructor(
    private val uri: URI,
    socketFactory: SSLSocketFactory
): SocketConnectionDataSource {

    companion object {

        private const val REPLAY_COUNT = 1
        private const val EXTRA_CAPACITY = 1

        private const val CONNECTION_TIMEOUT_MILLIS = 3000L
    }

    private val webSocketClient: WebSocketClient

    private val _socketMessages = MutableSharedFlow<ApiMessage>(
        replay = REPLAY_COUNT,
        extraBufferCapacity = EXTRA_CAPACITY,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    override val socketMessages: SharedFlow<ApiMessage>
    get() = _socketMessages

    init {

         webSocketClient = object: WebSocketClient(uri) {

            override fun onOpen(handshakedata: ServerHandshake?) {
                handshakedata ?: return
                val message = ApiMessage.StatusMessage.Open
                sendMessage(message)
                subscribeToApi(this)
            }

            override fun onMessage(message: String?) {
                message ?: return
                val apiMessage = ApiMessage.MessageReceived.TextMessageReceived(message)
                sendMessage(apiMessage)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                val message = ApiMessage.StatusMessage.Closed
                sendMessage(message)
            }

            override fun onError(ex: Exception?) {
                ex ?: return
                val message = ApiMessage.ErrorMessage.SocketError(ex)
                sendMessage(message)
            }
        }
        webSocketClient.setSocketFactory(socketFactory)
    }

    override suspend fun connect() {
        catchInterruptedException({
            webSocketClient.connectBlocking(CONNECTION_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        })
    }

    override suspend fun reconnect() {
        catchInterruptedException({
            webSocketClient.reconnectBlocking()
        })
    }

    override suspend fun disconnect() {
        catchInterruptedException({
            webSocketClient.closeBlocking()
        })
    }

    private fun sendMessage(message: ApiMessage) = runBlocking {
        _socketMessages.emit(message)
    }

    private fun subscribeToApi(socket: WebSocketClient) {
        val channelsToSubscribe = listOf("ticker")
        val productsToSubscribe = listOf(
            "BTC-USD", "BTC-EUR", "ETH-USD", "ETH-EUR",
            "MASK-USD", "MASK-EUR", "SUSHI-USD", "SUSHI-EUR"
        )
        val subscribeRequest = SubscribeRequest(
            channelsToSubscribe = channelsToSubscribe,
            productsToSubscribe = productsToSubscribe
        )

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(SubscribeRequest::class.java)
        val jsonString = adapter.toJson(subscribeRequest)
        socket.send(jsonString)
    }

    private fun catchInterruptedException(
        action: () -> Unit,
        onError: () -> Unit = {}
    ) = try {
        action.invoke()
    } catch (ex: InterruptedException) {
        onError.invoke()
        val message = ApiMessage.ErrorMessage.ConnectError(ex)
        sendMessage(message)
    }
}