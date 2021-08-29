package com.ferum_bot.cryptocharts.network

import com.ferum_bot.cryptocharts.core.enums.SocketCloseCodes

interface SocketConnectionController {

    fun connect()

    fun reconnect()

    fun disconnect(
        closeCode: SocketCloseCodes = SocketCloseCodes.NORMAL, reason: (() -> String)? = null
    )
}