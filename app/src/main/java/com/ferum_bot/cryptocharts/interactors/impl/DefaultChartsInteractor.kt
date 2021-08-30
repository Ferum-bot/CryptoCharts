package com.ferum_bot.cryptocharts.interactors.impl

import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.repositories.ChartsRepository
import javax.inject.Inject

class DefaultChartsInteractor @Inject constructor(
    private val repository: ChartsRepository,
): ChartsInteractor {

}