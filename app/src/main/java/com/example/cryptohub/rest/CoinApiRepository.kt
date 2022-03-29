package com.example.cryptohub.rest

import com.example.cryptohub.model.allcoins.CoinInfo
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import retrofit2.Response

class CoinApiRepositoryImpl(
    private val coinGeckoApi: CoinGeckoApi
) : CoinApiRepository {

    override suspend fun getCoinData(id: String): Response<CoinData> =
        coinGeckoApi.getCoinData(id)

    override suspend fun getAllCoins(): Response<List<CoinInfo>> =
        coinGeckoApi.getAllCoins()

    override suspend fun getTrendingCoins(): Response<TrendingCoins> =
        coinGeckoApi.getTrendingCoins()

    override suspend fun searchForCoins(query: String): Response<SearchCoins> =
        coinGeckoApi.searchForCoins(query)
}

interface CoinApiRepository {
    suspend fun getCoinData(id: String) : Response<CoinData>
    suspend fun getAllCoins() : Response<List<CoinInfo>>
    suspend fun getTrendingCoins() : Response<TrendingCoins>
    suspend fun searchForCoins(query : String) : Response<SearchCoins>
}