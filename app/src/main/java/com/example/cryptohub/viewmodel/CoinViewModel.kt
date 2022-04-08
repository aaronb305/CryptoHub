package com.example.cryptohub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.rest.CoinApiRepository
import com.example.cryptohub.utils.CoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CoinViewModel @Inject constructor(
    private val coinApiRepository: CoinApiRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(){

    init {
        Log.d("view model", "view model created")
    }

    var coin : CoinItem? = null
    var coinId : String? = null

    private val _coinMarketChart : MutableLiveData<CoinResponse> = MutableLiveData(CoinResponse.LOADING)
    val coinMarketChart : LiveData<CoinResponse> get() = _coinMarketChart

    private val _coinData : MutableLiveData<CoinResponse> = MutableLiveData(CoinResponse.LOADING)
    val coinData : LiveData<CoinResponse> get() = _coinData

    fun getTrendingCoins() {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getTrendingCoins().collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getCoinsByMarketCap(pageNumber : Int = 1) {
        _coinData.postValue(CoinResponse.LOADING)
        Log.d("view model", "entered function")
        viewModelScope.launch(dispatcher) {
            Log.d("view model", pageNumber.toString())
            coinApiRepository.getCoinsByMarketCap(pageNumber = pageNumber).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getCoinById(id: String) {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getCoinData(id).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun searchForCoins(query: String) {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.searchForCoins(query).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getExchanges(pageNumber: Int = 1) {
        Log.d("view model", "get exchanges called")
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getExchanges(pageNumber).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getDerivativeExchanges(pageNumber: Int = 1) {
        Log.d("view model", "get derivative exchanges called")
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getDerivativeExchanges(pageNumber).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getExchangeData(exchangeId : String, pageNumber: Int) {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getExchangeData(exchangeId, pageNumber).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getDerivativeExchangeData(id: String) {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getDerivativeExchangeData(id).collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getCoinChartData(id : String, days : String) {
        _coinData.postValue(CoinResponse.LOADING)
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getCoinChartData(id, days).collect {
                _coinMarketChart.postValue(it)
            }
        }
    }

    fun resetState() {
        _coinData.postValue(CoinResponse.DEFAULT)
    }
}