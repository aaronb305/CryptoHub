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

    override fun getCoinData(id: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getCoinDataById(id) },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun getTrendingCoins(): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getTrendingCoins() },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun searchForCoins(query: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.searchForCoins(query) },
                { emit(it) },
                { emit(it) }
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
                { emit(it) },
                { emit(it) }
            )
            Log.d("repository", "end flow")
        }

    override fun getExchanges(page: Int): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getExchanges(page) },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun getDerivativeExchanges(pageNumber: Int): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getDerivativeExchanges(pageNumber) },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun getExchangeData(exchangeId: String, pageNumber: Int): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getExchangeTickers(exchangeId, pageNumber) },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun getDerivativeExchangeData(id: String, includeTicker: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getDerivativeExchangeData(id) },
                { emit(it) },
                { emit(it) }
            )
        }

    override fun getCoinChartData(id : String, days: String): Flow<CoinResponse> =
        flow {
            responseTryCatch(
                { coinGeckoApi.getCoinChartData(id, days = days) },
                { emit(it) },
                { emit(it) }
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
    fun getExchangeData(exchangeId : String, pageNumber: Int) : Flow<CoinResponse>
    fun getDerivativeExchangeData(
        id : String, includeTicker : String = "unexpired"
    ) : Flow<CoinResponse>
    fun getCoinChartData(
        id : String,
        days : String
    ) : Flow<CoinResponse>
}