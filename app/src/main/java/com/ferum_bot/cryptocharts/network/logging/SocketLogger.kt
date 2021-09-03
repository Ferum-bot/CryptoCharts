package com.ferum_bot.cryptocharts.network.logging

import org.java_websocket.handshake.Handshakedata

interface SocketLogger {

    fun logMessage(message: String)

    fun logException(exception: Exception)

    fun logOpen(handShakeData: Handshakedata)

    fun logClose(code: Int, reason: String?, remote: Boolean)
}