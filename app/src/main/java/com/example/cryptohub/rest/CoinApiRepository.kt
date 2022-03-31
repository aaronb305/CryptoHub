package com.example.cryptohub.rest

import android.util.Log
import com.example.cryptohub.utils.CoinResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class CoinApiRepositoryImpl(
    private val coinGeckoApi: CoinGeckoApi
) : CoinApiRepository {

    override fun getCoinData(id: String): Flow<CoinResponse> =
        flow {
            responseTryCatch { coinGeckoApi.getCoinData(id) }
        }

    override fun getAllCoins(): Flow<CoinResponse> =
        flow {
            responseTryCatch {  coinGeckoApi.getAllCoins() }
        }

    override fun getTrendingCoins(): Flow<CoinResponse> =
        flow {
            responseTryCatch { coinGeckoApi.getTrendingCoins() }
        }

    override fun searchForCoins(query: String): Flow<CoinResponse> =
        flow {
            responseTryCatch {  coinGeckoApi.searchForCoins(query) }
        }

    override fun getCoinsByMarketCap(
        currency: String,
        order: String,
        amount: Int,
        pageNumber: Int
    ): Flow<CoinResponse> =
        flow {
            Log.d("repository", "started flow")
            responseTryCatch { coinGeckoApi.getCoinsByMarketCap(pageNumber = pageNumber) }
            Log.d("repository", "end flow")
        }

    private val _coinResponse: MutableStateFlow<CoinResponse> =
        MutableStateFlow(CoinResponse.LOADING)

    override val coinResponse : StateFlow<CoinResponse>
    get() = _coinResponse

    private suspend fun <T> responseTryCatch(response : suspend () -> Response<T>)  {
        Log.d("utils", "entered response try catch")
        try {
            val result = response()
            Log.d("utils", "entered response try block")
            if (result.isSuccessful) {
                Log.d("utils", "entered response success")
                result.body()?.let {
                    _coinResponse.value = CoinResponse.SUCCESS(it)
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
            _coinResponse.value = CoinResponse.ERROR(e)
        }
    }
}

interface CoinApiRepository {
    fun getCoinData(id: String) : Flow<CoinResponse>
    fun getAllCoins() : Flow<CoinResponse>
    fun getTrendingCoins() : Flow<CoinResponse>
    fun searchForCoins(query : String) : Flow<CoinResponse>
    fun getCoinsByMarketCap(
        currency : String = "usd",
        order : String = "market_cap_desc",
        amount : Int = 100,
        pageNumber : Int = 1
    ) : Flow<CoinResponse>

    val coinResponse : StateFlow<CoinResponse>
}