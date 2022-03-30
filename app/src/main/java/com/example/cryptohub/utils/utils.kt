package com.example.cryptohub.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.crypto.SealedObject

//fun <T> responseTryCatch(response : Response<T>) : Flow<T> {
//    return flow {
//        try {
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(it)
//                } ?: throw Exception("Response is null")
//            }
//            else {
//                throw Exception("Unsuccessful response")
//            }
//        }
//        catch (e: Exception) {
//            emit()
//        }
//    }
//}