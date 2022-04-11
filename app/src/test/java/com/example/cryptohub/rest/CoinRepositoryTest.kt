package com.example.cryptohub.rest

import com.example.cryptohub.model.coinchartdata.CoinChartData
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.model.derivatives.DerivativeExchange
import com.example.cryptohub.model.derivatives.DerivativeExchangeData
import com.example.cryptohub.model.exchanges.Exchange
import com.example.cryptohub.model.exchanges.ExchangeData
import com.example.cryptohub.model.exchanges.Ticker
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.Coin
import com.example.cryptohub.model.trending.TrendingCoins
import com.example.cryptohub.utils.CoinResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinRepositoryTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private val mockApi = mockk<CoinGeckoApi>(relaxed = true)
    private val mockTrendingCoin = mockk<Coin>(relaxed = true)
    private val mockSearchCoin = mockk<com.example.cryptohub.model.search.Coin>()
    private val mockCoinsByMarketCap = mockk<CoinItem>()
    private val mockExchange = mockk<Exchange>()
    private val mockDerivativeExchange = mockk<DerivativeExchange>()
    private val mockExchangeTicker = mockk<Ticker>()
    private val mockDerivativeTicker = mockk<com.example.cryptohub.model.derivatives.Ticker>()
    private val mockCoinChartData = mockk<List<Double>>()

    private lateinit var target: CoinApiRepository

    @Before
    fun startup() {
        Dispatchers.setMain(testDispatcher)
        target = CoinApiRepositoryImpl(mockApi)
    }

    @After
    fun shutdown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `get coin data by id when calling the server returns success response`() = runTest {
       coEvery { mockApi.getCoinDataById("") } returns mockk {
           every { isSuccessful } returns true
           every { body() } returns mockk {
               every { marketCapRank } returns 1
               every { symbol } returns "Expected result"
           }
       }

       target.getCoinData("").collect {
           val success = it as CoinResponse.SUCCESS<CoinData>
           assertThat(success.response).isNotNull()
           assertThat(success.response.marketCapRank).isEqualTo(1)
           assertThat(success.response.symbol).isEqualTo("Expected result")
       }

       coVerify { mockApi.getCoinDataById("") }
    }

    @Test
    fun `get coin data by id when calling the server returns error response`() = runTest {
        coEvery { mockApi.getCoinDataById("") } returns mockk {
            every { isSuccessful } returns false
        }

        target.getCoinData("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getCoinDataById("") }
    }

    @Test
    fun `get coin data by id when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getCoinDataById("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getCoinData("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getCoinDataById("") }
    }

    @Test
    fun `get trending coins when calling the server returns success response`() = runTest {
        coEvery { mockApi.getTrendingCoins() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { coins } returns listOf(mockTrendingCoin)
            }
        }

        target.getTrendingCoins().collect {
            val success = it as CoinResponse.SUCCESS<TrendingCoins>
            assertThat(success.response).isNotNull()
            assertThat(success.response.coins).isEqualTo(listOf(mockTrendingCoin))
        }

        coVerify { mockApi.getTrendingCoins() }
    }

    @Test
    fun `get trending coins when calling the server returns error response`() = runTest {
        coEvery { mockApi.getTrendingCoins() } returns mockk {
            every { isSuccessful } returns false
        }

        target.getTrendingCoins().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getTrendingCoins() }
    }

    @Test
    fun `get trending coins when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getTrendingCoins() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getTrendingCoins().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getTrendingCoins() }
    }

    @Test
    fun `search for coins when calling the server returns success response`() = runTest {
        coEvery { mockApi.searchForCoins("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { coins } returns listOf(mockSearchCoin)
            }
        }

        target.searchForCoins("").collect {
            val success = it as CoinResponse.SUCCESS<SearchCoins>
            assertThat(success.response).isNotNull()
            assertThat(success.response.coins).isEqualTo(listOf(mockSearchCoin))
        }

        coVerify { mockApi.searchForCoins("") }
    }

    @Test
    fun `search for coins when calling the server returns error response`() = runTest {
        coEvery { mockApi.searchForCoins("") } returns mockk {
            every { isSuccessful } returns false
        }

        target.searchForCoins("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.searchForCoins("") }
    }

    @Test
    fun `search for coins when calling the server returns null body response`() = runTest {
        coEvery { mockApi.searchForCoins("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.searchForCoins("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.searchForCoins("") }
    }

    @Test
    fun `get coins by market cap when calling the server returns success response`() = runTest {
        coEvery { mockApi.getCoinsByMarketCap() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { this@mockk } returns listOf(mockCoinsByMarketCap)
            }
        }

        target.getCoinsByMarketCap().collect {
            val success = it as CoinResponse.SUCCESS<List<CoinItem>>
            assertThat(success.response).isNotNull()
            assertThat(success.response).isEqualTo(listOf(mockCoinsByMarketCap))
        }

        coVerify { mockApi.getCoinsByMarketCap() }
    }

    @Test
    fun `get coins by market cap when calling the server returns error response`() = runTest {
        coEvery { mockApi.getCoinsByMarketCap() } returns mockk {
            every { isSuccessful } returns false
        }

        target.getCoinsByMarketCap().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getCoinsByMarketCap() }
    }

    @Test
    fun `get coins by market cap when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getCoinsByMarketCap() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getCoinsByMarketCap().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getCoinsByMarketCap() }
    }

    @Test
    fun `get exchanges when calling the server returns success response`() = runTest {
        coEvery { mockApi.getExchanges() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { this@mockk } returns listOf(mockExchange)
            }
        }

        target.getExchanges().collect {
            val success = it as CoinResponse.SUCCESS<List<Exchange>>
            assertThat(success.response).isNotNull()
            assertThat(success.response).isEqualTo(listOf(mockExchange))
        }

        coVerify { mockApi.getExchanges() }
    }

    @Test
    fun `get exchanges when calling the server returns error response`() = runTest {
        coEvery { mockApi.getExchanges() } returns mockk {
            every { isSuccessful } returns false
        }

        target.getExchanges().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getExchanges() }
    }

    @Test
    fun `get exchanges when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getExchanges() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getExchanges().collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getExchanges() }
    }

    @Test
    fun `get derivative exchanges when calling the server returns success response`() = runTest {
        coEvery { mockApi.getDerivativeExchanges() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { this@mockk } returns listOf(mockDerivativeExchange)
            }
        }

        target.getDerivativeExchanges(1).collect {
            val success = it as CoinResponse.SUCCESS<List<DerivativeExchange>>
            assertThat(success.response).isNotNull()
            assertThat(success.response).isEqualTo(listOf(mockDerivativeExchange))

        }

        coVerify { mockApi.getDerivativeExchanges() }
    }

    @Test
    fun `get derivative exchanges when calling the server returns error response`() = runTest {
        coEvery { mockApi.getDerivativeExchanges() } returns mockk {
            every { isSuccessful } returns false
        }

        target.getDerivativeExchanges(1).collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getDerivativeExchanges() }
    }

    @Test
    fun `get derivative exchanges when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getDerivativeExchanges() } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getDerivativeExchanges(1).collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getDerivativeExchanges() }
    }

    @Test
    fun `get exchange data when calling the server returns success response`() = runTest {
        coEvery { mockApi.getExchangeTickers("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { tickers } returns listOf(mockExchangeTicker)
            }
        }

        target.getExchangeData("", 1).collect {
            val success = it as CoinResponse.SUCCESS<ExchangeData>
            assertThat(success.response).isNotNull()
            assertThat(success.response.tickers).isEqualTo(listOf(mockExchangeTicker))
        }

        coVerify { mockApi.getExchangeTickers("") }
    }

    @Test
    fun `get exchange data when calling the server returns error response`() = runTest {
        coEvery { mockApi.getExchangeTickers("") } returns mockk {
            every { isSuccessful } returns false
        }

        target.getExchangeData("", 1).collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getExchangeTickers("") }
    }

    @Test
    fun `get exchange data when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getExchangeTickers("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getExchangeData("", 1).collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getExchangeTickers("") }
    }

    @Test
    fun `get derivative exchange data when calling the server returns success response`() = runTest {
        coEvery { mockApi.getDerivativeExchangeData("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { tickers } returns listOf(mockDerivativeTicker)
            }
        }

        target.getDerivativeExchangeData("").collect {
            val success = it as CoinResponse.SUCCESS<DerivativeExchangeData>
            assertThat(success.response).isNotNull()
            assertThat(success.response.tickers).isEqualTo(listOf(mockDerivativeTicker))
        }

        coVerify { mockApi.getDerivativeExchangeData("") }
    }

    @Test
    fun `get derivative exchange data when calling the server returns error response`() = runTest {
        coEvery { mockApi.getDerivativeExchangeData("") } returns mockk {
            every { isSuccessful } returns false
        }

        target.getDerivativeExchangeData("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getDerivativeExchangeData("") }
    }

    @Test
    fun `get derivative exchange data when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getDerivativeExchangeData("") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getDerivativeExchangeData("").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getDerivativeExchangeData("") }
    }

    @Test
    fun `get coin chart data when calling the server returns success response`() = runTest {
        coEvery { mockApi.getCoinChartData("", "") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { prices } returns listOf(mockCoinChartData)
            }
        }

        target.getCoinChartData("", "").collect {
            val success = it as CoinResponse.SUCCESS<CoinChartData>
            assertThat(success.response).isNotNull()
            assertThat(success.response.prices).isEqualTo(mockCoinChartData)
        }

        coVerify { mockApi.getCoinChartData("", "") }
    }

    @Test
    fun `get coin chart data when calling the server returns error response`() = runTest {
        coEvery { mockApi.getCoinChartData("", "") } returns mockk {
            every { isSuccessful } returns false
        }

        target.getCoinChartData("", "").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Unsuccessful response")
        }

        coVerify { mockApi.getCoinChartData("", "") }
    }

    @Test
    fun `get coin chart data when calling the server returns null body response`() = runTest {
        coEvery { mockApi.getCoinChartData("", "") } returns mockk {
            every { isSuccessful } returns true
            every { body() } returns null
        }

        target.getCoinChartData("", "").collect {
            val error = it as CoinResponse.ERROR
            assertThat(error.error).isNotNull()
            assertThat(error.error.localizedMessage).isEqualTo("Response is null")
        }

        coVerify { mockApi.getCoinChartData("", "") }
    }
}