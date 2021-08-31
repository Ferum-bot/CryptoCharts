package com.ferum_bot.cryptocharts.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
@MustBeDocumented
annotation class StatusParser

@Qualifier
@Retention(RUNTIME)
@MustBeDocumented
annotation class TickerParser

@Qualifier
@Retention(RUNTIME)
@MustBeDocumented
annotation class ExceptionParser
