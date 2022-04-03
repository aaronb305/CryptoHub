package com.example.cryptohub.rest

import android.util.Log
import com.example.cryptohub.utils.CoinResponse
import com.example.cryptohub.utils.responseTryCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception

class CoinApiRepositoryImpl(
    private val coinGeckoApi: CoinGeckoApi
) : CoinApiRepository {

    private val _coinResponse: MutableStateFlow<CoinResponse> =
        MutableStateFlow(CoinResponse.LOADING)

    override val coinResponse : StateFlow<CoinResponse>
        get() = _coinResponse

    override fun getCoinData(id: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getCoinDataById(id) },
                { _coinResponse.value = it },
                { _coinResponse.value = it }
            )
        }

    override fun getTrendingCoins(): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getTrendingCoins() },
                { _coinResponse.value = it},
                { _coinResponse.value = it }
            )
        }

    override fun searchForCoins(query: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.searchForCoins(query) },
                { _coinResponse.value = it},
                { _coinResponse.value = it }
            )
        }

    override fun getCoinsByMarketCap(
        currency: String,
        order: String,
        amount: Int,
        pageNumber: Int
    ): Flow<CoinResponse> =
        flow {
            Log.d("repository", "started flow")
            responseTryCatch(
                { coinGeckoApi.getCoinsByMarketCap(pageNumber = pageNumber) },
                { _coinResponse.value = it },
                { _coinResponse.value = it }
            )
            Log.d("repository", "end flow")
        }

    override fun getExchanges(page: Int): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getExchanges(page) },
                { _coinResponse.value = it },
                { _coinResponse.value = it }
            )
        }

    override fun getDerivativeExchanges(pageNumber: Int): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getDerivativeExchanges(pageNumber) },
                { _coinResponse.value = it },
                { _coinResponse.value = it }
            )
        }
}

interface CoinApiRepository {
    fun getCoinData(id: String) : Flow<CoinResponse>
    fun getTrendingCoins() : Flow<CoinResponse>
    fun searchForCoins(query : String) : Flow<CoinResponse>
    fun getCoinsByMarketCap(
        currency : String = "usd",
        order : String = "market_cap_desc",
        amount : Int = 100,
        pageNumber : Int = 1
    ) : Flow<CoinResponse>
    fun getExchanges(page : Int = 1) : Flow<CoinResponse>
    fun getDerivativeExchanges(pageNumber: Int) : Flow<CoinResponse>

    val coinResponse : StateFlow<CoinResponse>
}