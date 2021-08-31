package com.ferum_bot.cryptocharts.ui.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferum_bot.cryptocharts.core.models.Ticker
import com.ferum_bot.cryptocharts.network.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.ferum_bot.cryptocharts.use_cases.adapters.TickerSizeAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChartsViewModel @Inject constructor(
    private val interactor: ChartsInteractor,
    private val sizeAdapter: TickerSizeAdapter,
): ViewModel() {

    private val _connectionStatus: MutableLiveData<SocketConnectionStatus> = MutableLiveData()
    val connectionStatus: LiveData<SocketConnectionStatus> = _connectionStatus

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

        viewModelScope.launch {
            interactor.currentStatus.collect { status ->
                handleInComingStatus(status)
            }
        }
    }

    fun connect() {
        _connectionStatus.value = SocketConnectionStatus.CONNECTING

        connectionJob?.cancel()
        connectionJob = viewModelScope.launch {
            interactor.connect()
        }
    }

    fun reconnect() {
        _connectionStatus.value = SocketConnectionStatus.CONNECTING

        connectionJob?.cancel()
        connectionJob = viewModelScope.launch {
            interactor.reconnect()
        }
    }

    fun disconnect() {
        connectionJob?.cancel()
        connectionJob = viewModelScope.launch {
            interactor.disconnect()
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

    private fun handleInComingStatus(status: SocketConnectionStatus) {
        _connectionStatus.postValue(status)
    }
}