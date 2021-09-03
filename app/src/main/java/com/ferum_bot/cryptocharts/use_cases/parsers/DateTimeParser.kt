package com.ferum_bot.cryptocharts.use_cases.parsers

import java.text.SimpleDateFormat
import java.util.*

interface DateTimeParser {

    fun parseFrom(rawTime: String): Date?

    fun getFrom(date: Date, format: SimpleDateFormat): String
}