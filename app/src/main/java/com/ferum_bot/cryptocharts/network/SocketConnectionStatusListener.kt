package com.ferum_bot.cryptocharts.network

import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketFrame
import com.neovisionaries.ws.client.WebSocketState

interface SocketConnectionStatusListener {

    fun onConnected(webSocket: WebSocket, headers: Map<String, List<String>>)

    fun onDisconnected(
        webSocket: WebSocket, closedByServer: Boolean,
        serverCloseFrame: WebSocketFrame, clientCloseFrame: WebSocketFrame,
    )

    fun onStateChanged(webSocket: WebSocket, newState: WebSocketState)
}