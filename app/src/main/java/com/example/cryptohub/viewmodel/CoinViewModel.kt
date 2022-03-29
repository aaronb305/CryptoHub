package com.example.cryptohub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptohub.rest.CoinApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception

@HiltViewModel
class CoinViewModel(
    private val coinApiRepository: CoinApiRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel(){

    private val _coinData : MutableLiveData<CoinResponse> = MutableLiveData(CoinResponse.LOADING)
    val coinData : LiveData<CoinResponse> get() = _coinData

    fun getTrendingCoins() {
        viewModelScope.launch(dispatcher) {
            try {
                val response = coinApiRepository.getTrendingCoins()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _coinData.postValue(CoinResponse.SUCCESS(it))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("Unsuccessful response")
                }
            }
            catch (e: Exception) {
                _coinData.postValue(CoinResponse.ERROR(e))
            }
        }
    }
}