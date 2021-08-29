package com.ferum_bot.cryptocharts.network.models

import android.os.Parcelable
import com.ferum_bot.cryptocharts.core.models.Channel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SubscriptionsResponse(

    @Json(name = "type")
    override val type: String = "subscriptions",

    @Json(name = "channels")
    val subscribedChannels: List<Channel> = emptyList()
): Parcelable, BaseApiModel
