package com.ferum_bot.cryptocharts.network.logging

import org.java_websocket.handshake.Handshakedata
import timber.log.Timber

class TimberSocketLogger: SocketLogger {

    override fun logMessage(message: String) {
        Timber.tag("Socket Message ").i(message)
    }

    override fun logException(exception: Exception) {
        Timber.tag("Socket Exception ").e(exception)
    }

    override fun logOpen(handShakeData: Handshakedata) {
        Timber.tag("Socket Open ").i(handShakeData.toString())
    }

    override fun logClose(code: Int, reason: String?, remote: Boolean) {
        Timber.tag("Socket Close ").i("Code: $code, Reason: $reason, Remote: $remote")
    }
}