package com.example.cryptohub.rest

import com.example.cryptohub.model.allcoins.CoinInfo
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import com.example.cryptohub.utils.CoinResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class CoinApiRepositoryImpl(
    private val coinGeckoApi: CoinGeckoApi
) : CoinApiRepository {

    override suspend fun getCoinData(id: String): Response<CoinData> =
        coinGeckoApi.getCoinData(id)

    override fun getAllCoins(): Flow<CoinResponse> =
        flow {
            val response = coinGeckoApi.getAllCoins()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(CoinResponse.SUCCESS(it))
                    } ?: throw Exception("Response is null")
                }
                else {
                    throw Exception("Unsuccessful response")
                }
            }
            catch (e: Exception) {
                emit(CoinResponse.ERROR(e))
            }
        }

//    override fun getTrendingCoins(): Flow<CoinResponse> =
//        responseTryCatch<CoinResponse>(
//            coinGeckoApi.getTrendingCoins()
//        )

    override suspend fun searchForCoins(query: String): Response<SearchCoins> =
        coinGeckoApi.searchForCoins(query)
}

interface CoinApiRepository {
    suspend fun getCoinData(id: String) : Response<CoinData>
    fun getAllCoins() : Flow<CoinResponse>
//    fun getTrendingCoins() : Flow<CoinResponse>
    suspend fun searchForCoins(query : String) : Response<SearchCoins>
}