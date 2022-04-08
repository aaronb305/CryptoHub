package com.example.cryptohub.rest

import com.example.cryptohub.model.coinchartdata.CoinChartData
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.model.derivatives.DerivativeExchange
import com.example.cryptohub.model.derivatives.DerivativeExchangeData
import com.example.cryptohub.model.exchanges.Exchange
import com.example.cryptohub.model.exchanges.ExchangeData
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import com.example.cryptohub.rest.CoinGeckoApi.Companion.DERIVATIVES_PATH
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("$COINS_PATH{id}")
    suspend fun getCoinDataById(
        @Path("id") id: String
    ) : Response<CoinData>

    @GET("$COINS_PATH{id}/market_chart")
    suspend fun getCoinChartData(
        @Path("id") id : String,
        @Query("days") days : String,
        @Query("vs_currency") currency: String = "usd"
    ) : Response<CoinChartData>

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

    @GET(EXCHANGES_PATH)
    suspend fun getExchanges(
        @Query("page") pageNumber: Int = 1
    ) : Response<List<Exchange>>

    @GET("$EXCHANGES_PATH{id}/tickers")
    suspend fun getExchangeTickers(
        @Path("id") exchangeId : String,
        @Query("page") pageNumber: Int = 1
    ) : Response<ExchangeData>

    @GET(DERIVATIVES_PATH)
    suspend fun getDerivativeExchanges(
        @Query("page") pageNumber: Int = 1
    ) : Response<List<DerivativeExchange>>

    @GET("$DERIVATIVES_PATH{id}")
    suspend fun getDerivativeExchangeData(
        @Path("id") derivativeExchangeId : String,
        @Query("include_tickers") tickers : String = "unexpired"
    ) : Response<DerivativeExchangeData>

    companion object {
        const val BASE_URL = "https://api.coingecko.com/api/v3/"
        private const val DERIVATIVES_PATH = "derivatives/exchanges/"
        private const val EXCHANGES_PATH = "exchanges/"
        private const val COINS_PATH = "coins/"
        private const val COINS_BY_MARKET_CAP = "${COINS_PATH}markets/"
        private const val SEARCH_PATH = "search/"
        private const val TRENDING_PATH = "${SEARCH_PATH}trending/"
    }
}
