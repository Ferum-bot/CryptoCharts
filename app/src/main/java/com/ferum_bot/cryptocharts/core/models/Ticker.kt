package com.ferum_bot.cryptocharts.core.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Ticker(

    @Json(name = "type")
    val type: String = "",

    @Json(name = "sequence")
    val sequence: Long = 0L,

    @Json(name = "product_id")
    val productName: String = "",

    @Json(name = "price")
    val currentPrice: String = "",

    @Json(name = "open_24h")
    val openDayPrice: String = "",

    @Json(name = "volume_24h")
    val volumeDayPrice: String = "",

    @Json(name = "low_24h")
    val lowDayPrice: String = "",

    @Json(name = "high_24h")
    val highDayPrice: String = "",

    @Json(name = "best_bid")
    val bestBid: String = "",

    @Json(name = "best_ask")
    val bestAsk: String = "",

    @Json(name = "time")
    val time: String = "",

    @Json(name = "side")
    val side: String = "",

): Parcelable
