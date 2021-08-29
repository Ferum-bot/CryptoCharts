package com.ferum_bot.cryptocharts.network.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UnsubscribeRequest(

    @Json(name = "type")
    override val type: String,

    @Json(name = "product_ids")
    val productsToUnsubscribe: List<String>? = null,

    @Json(name = "channels")
    val channelsToUnsubscribe: List<String>? = null,
): Parcelable, BaseApiModel
