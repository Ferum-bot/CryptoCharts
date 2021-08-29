package com.ferum_bot.cryptocharts.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SubscribeRequest(

    @Json(name = "type")
    override val type: String = "subscribe",

    @Json(name = "product_ids")
    val productsToSubscribe: List<String> = emptyList(),

    @Json(name = "channels")
    val channelsToSubscribe: List<String> = emptyList(),

): Parcelable, BaseApiModel
