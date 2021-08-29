package com.ferum_bot.cryptocharts.core.extensions

import android.content.Context

fun Context.dpToPx(dp: Int): Int {
    val density = resources.displayMetrics.density
    return (dp.toFloat() *  density + 0.5f).toInt()
}

fun Context.pxToDp(px: Int): Int {
    val density = resources.displayMetrics.density
    return (px.toFloat() /  density + 0.5f).toInt()
}