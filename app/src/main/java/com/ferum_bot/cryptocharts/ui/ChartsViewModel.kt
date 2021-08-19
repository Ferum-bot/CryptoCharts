package com.ferum_bot.cryptocharts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferum_bot.cryptocharts.core.enums.SocketConnectionStatus
import com.ferum_bot.cryptocharts.interactors.ChartsInteractor
import com.neovisionaries.ws.client.WebSocketAdapter
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChartsViewModel @Inject constructor(
    private val interactor: ChartsInteractor,
): ViewModel() {

    private val _networkStatus: MutableLiveData<SocketConnectionStatus> = MutableLiveData()
    val networkStatus: LiveData<SocketConnectionStatus> = _networkStatus

    private val _errorMessage: MutableLiveData<String?> = MutableLiveData(null)
    val errorMessage: LiveData<String?> = _errorMessage

    private var connectionJob: Job? = null

    init {
        connect()

        object: WebSocketAdapter() {

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
}