package com.example.cryptohub.rest

import com.example.cryptohub.model.allcoins.CoinInfo
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("$COINS_PATH{id}")
    suspend fun getCoinData(
        @Path("id") id: String
    ) : Response<CoinData>

    @GET(ALL_COINS_PATH)
    suspend fun getAllCoins() : Response<List<CoinInfo>>

    @GET(COINS_BY_MARKET_CAP)
    suspend fun getCoinsByMarketCap(
        @Query("vs_currency") currency : String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") amount : Int = 100,
        @Query("page") pageNumber : Int = 1
    ) : Response<List<CoinItem>>

    @GET(SEARCH_PATH)
    suspend fun searchForCoins(
        @Query("query") query: String
    ) : Response<SearchCoins>

    @GET(TRENDING_PATH)
    suspend fun getTrendingCoins() : Response<TrendingCoins>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        private const val COINS_PATH = "coins/"
        private const val COINS_BY_MARKET_CAP = "${COINS_PATH}markets/"
        private const val ALL_COINS_PATH = "${COINS_PATH}list/"
        private const val SEARCH_PATH = "search/"
        private const val TRENDING_PATH = "${SEARCH_PATH}trending/"
    }
}
