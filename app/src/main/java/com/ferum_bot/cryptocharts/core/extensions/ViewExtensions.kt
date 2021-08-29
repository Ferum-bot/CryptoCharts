package com.ferum_bot.cryptocharts.core.extensions

import android.text.Layout
import android.view.LayoutInflater
import android.view.View

val View.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)