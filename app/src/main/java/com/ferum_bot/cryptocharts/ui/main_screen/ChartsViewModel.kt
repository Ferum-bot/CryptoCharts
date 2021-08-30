package com.ferum_bot.cryptocharts.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.use_cases.TickerSizeAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChartsViewModel @Inject constructor(
    private val interactor: ChartsInteractor,
    private val sizeAdapter: TickerSizeAdapter,
): ViewModel() {

    private val _networkStatus: MutableLiveData<SocketConnectionStatus> = MutableLiveData()
    val networkStatus: LiveData<SocketConnectionStatus> = _networkStatus

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private val _currentTickers: MutableLiveData<List<Ticker>> = MutableLiveData(emptyList())
    val currentTickers: LiveData<List<Ticker>> = _currentTickers

    private var connectionJob: Job? = null

    init {
        connect()

        viewModelScope.launch {
            interactor.inComingTickers.collect { ticker ->
                handleInComingTicker(ticker)
            }
        }

        viewModelScope.launch {
            interactor.exceptions.collect { exception ->
                handleInComingException(exception)
            }
        }
    }

    fun connect() {
        _networkStatus.value = SocketConnectionStatus.CONNECTING

        connectionJob?.cancel()
        connectionJob = viewModelScope.launch {

        }
    }

    fun reconnect() {
        _networkStatus.value = SocketConnectionStatus.CONNECTING

        connectionJob?.cancel()
        connectionJob = viewModelScope.launch {

        }
    }

    fun errorMessageHasShown() {
        _errorMessage.value = null
    }

    private fun handleInComingTicker(ticker: Ticker) {
        val tickers = currentTickers.value.orEmpty().toMutableList()
        tickers.add(0, ticker)
        val actualTickers = sizeAdapter.adaptLatTickers(tickers)
        _currentTickers.postValue(actualTickers)
    }

    private fun handleInComingException(exception: Exception) {
        _errorMessage.postValue(exception.message)
    }
}