package com.example.cryptohub.viewmodel

sealed class CoinResponse {
    object LOADING : CoinResponse()
    class SUCCESS<T>(val response : T) : CoinResponse()
    class ERROR(val error: Throwable) : CoinResponse()
    object DEFAULT : CoinResponse()
}
