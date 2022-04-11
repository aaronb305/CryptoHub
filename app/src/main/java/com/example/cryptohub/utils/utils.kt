package com.example.cryptohub.utils

import android.util.Log
import com.example.cryptohub.rest.CoinApiRepository
import com.example.cryptohub.rest.CoinApiRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

inline fun <In> responseTryCatch(
    response : () -> Response<In>,
    onSuccess : (CoinResponse.SUCCESS<In>) -> Unit,
    onError : (CoinResponse.ERROR) -> Unit
)  {
    try {
        val result = response()
        if (result.isSuccessful) {
            result.body()?.let {
                onSuccess(CoinResponse.SUCCESS(it))
            } ?: throw Exception("Response is null")
        }
        else {
            throw Exception("Unsuccessful response")
        }
    }
    catch (e: Exception) {
        onError(CoinResponse.ERROR(e))
    }
}