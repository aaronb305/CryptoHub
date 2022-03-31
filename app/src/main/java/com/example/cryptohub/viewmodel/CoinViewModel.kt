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

    private val _coinData : MutableLiveData<CoinResponse> = MutableLiveData(CoinResponse.LOADING)
    val coinData : LiveData<CoinResponse> get() = _coinData

    fun getTrendingCoins() {
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getTrendingCoins().collect()
            coinApiRepository.coinResponse.collect {
                _coinData.postValue(it)
            }
        }
    }

    fun getCoinsByMarketCap(pageNumber : Int = 1) {
        _coinData.postValue(CoinResponse.LOADING)
        Log.d("view model", "entered function")
        viewModelScope.launch(dispatcher) {
            Log.d("view model", pageNumber.toString())
            coinApiRepository.getCoinsByMarketCap(pageNumber = pageNumber).collect()
            coinApiRepository.coinResponse.collect {
                Log.d("view model", "response value posted to live data")
                _coinData.postValue(it)
            }
        }
    }

    fun getAllCoins() {
        viewModelScope.launch(dispatcher) {
            coinApiRepository.getAllCoins().collect {
                _coinData.postValue(it)
            }
        }
    }

    fun resetState() {
        _coinData.postValue(CoinResponse.DEFAULT)
    }
}