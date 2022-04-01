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
    Log.d("utils", "entered response try catch")
        try {
            val result = response()
            Log.d("utils", "entered response try block")
            if (result.isSuccessful) {
                Log.d("utils", "entered response success")
                result.body()?.let {
                    onSuccess(CoinResponse.SUCCESS(it))
                } ?: throw Exception("Response is null")
            }
            else {
                Log.d("utils", "entered response failed")
                throw Exception("Unsuccessful response")
            }
        }
        catch (e: Exception) {
            Log.d("utils", "entered response catch block")
            Log.e("utils", e.localizedMessage)
            onError(CoinResponse.ERROR(e))
        }
}