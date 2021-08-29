package com.ferum_bot.cryptocharts.core.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Channel(

    @Json(name = "name")
    val name: String,

    @Json(name = "product_ids")
    val products: List<String> = emptyList()

): Parcelable
