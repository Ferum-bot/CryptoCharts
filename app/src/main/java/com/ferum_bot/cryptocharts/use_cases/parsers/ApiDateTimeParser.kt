package com.ferum_bot.cryptocharts.use_cases.parsers

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ApiDateTimeParser: DateTimeParser {

    companion object {

        private const val API_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    }

    private val apiDateFormat = SimpleDateFormat(API_DATE_PATTERN, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    override fun parseFrom(rawTime: String): Date? {
        return apiDateFormat.parse(rawTime)
    }

    override fun getFrom(date: Date, format: SimpleDateFormat): String {
        return format.format(date)
    }
}