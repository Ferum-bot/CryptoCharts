package com.ferum_bot.cryptocharts.network.enums

enum class ApiEventTypes(val string: String) {
    ERROR("error"),
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    SUBSCRIPTIONS("subscriptions"),
    STATUS("status"),
    TICKER("ticker");
}