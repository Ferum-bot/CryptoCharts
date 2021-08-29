package com.ferum_bot.cryptocharts.core.enums

import com.neovisionaries.ws.client.WebSocketCloseCode

enum class SocketCloseCodes {
    NORMAL, AWAY, UN_CONFORMED,
    UNACCEPTABLE, NONE, ABNORMAL,
    INCONSISTENT, VIOLATED,
    OVERSIZE, UN_EXTENDED,
    UNEXPECTED, INSECURE;
}