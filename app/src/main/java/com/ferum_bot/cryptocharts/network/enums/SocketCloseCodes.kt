package com.ferum_bot.cryptocharts.network.enums

enum class SocketCloseCodes {
    NORMAL, AWAY, UN_CONFORMED,
    UNACCEPTABLE, NONE, ABNORMAL,
    INCONSISTENT, VIOLATED,
    OVERSIZE, UN_EXTENDED,
    UNEXPECTED, INSECURE;
}