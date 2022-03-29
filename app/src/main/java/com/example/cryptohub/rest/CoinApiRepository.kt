package com.example.cryptohub.rest

import com.example.cryptohub.model.allcoins.CoinInfo
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CoinApiRepositoryImpl(
    private val coinGeckoApi: CoinGeckoApi
) : CoinApiRepository {

    override suspend fun getCoinData(id: String): Response<CoinData> =
        coinGeckoApi.getCoinData(id)

    override fun getAllCoins(): Flow<List<CoinInfo>> =
        flow {
            // your logic to get the response will be here
            coinGeckoApi.getAllCoins()
        }

    override fun getTrendingCoins(): Flow<List<TrendingCoins>> =
        flow {
            // your logic to get the response will be here
            coinGeckoApi.getTrendingCoins()
        }

    override suspend fun searchForCoins(query: String): Response<SearchCoins> =
        coinGeckoApi.searchForCoins(query)
}

interface CoinApiRepository {
    suspend fun getCoinData(id: String) : Response<CoinData>
    fun getAllCoins() : Flow<List<CoinInfo>>
    fun getTrendingCoins() : Flow<List<TrendingCoins>>
    suspend fun searchForCoins(query : String) : Response<SearchCoins>
}