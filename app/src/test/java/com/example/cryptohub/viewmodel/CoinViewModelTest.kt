package com.example.cryptohub.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cryptohub.model.coinchartdata.CoinChartData
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.model.derivatives.DerivativeExchange
import com.example.cryptohub.model.derivatives.DerivativeExchangeData
import com.example.cryptohub.model.exchanges.ExchangeData
import com.example.cryptohub.model.search.Exchange
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.model.trending.TrendingCoins
import com.example.cryptohub.rest.CoinApiRepository
import com.example.cryptohub.utils.CoinResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import java.lang.Exception

@ExperimentalCoroutinesApi
class CoinViewModelTest {

    @get:Rule var rule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockRepository = mockk<CoinApiRepository>(relaxed = true)

    private lateinit var target : CoinViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        target = CoinViewModel(mockRepository, testDispatcher)
    }

    @After
    fun shutdown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `get trending coins when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getTrendingCoins()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get trending coins when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getTrendingCoins() } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getTrendingCoins()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get trending coins when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getTrendingCoins() } returns flowOf(
            CoinResponse.SUCCESS(mockk<TrendingCoins>())
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getTrendingCoins()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get coins by market cap when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinsByMarketCap()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get coins by market cap when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinsByMarketCap() } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinsByMarketCap()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get coins by market cap when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinsByMarketCap() } returns flowOf(
            CoinResponse.SUCCESS(listOf(mockk<CoinItem>()))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinsByMarketCap()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get coins by id when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinById("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get coins by id when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinData("") } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinById("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get coins by id when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinData("") } returns flowOf(
            CoinResponse.SUCCESS(mockk<CoinData>())
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinById("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `search for coins when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.searchForCoins("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `search for coins when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.searchForCoins("") } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.searchForCoins("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `search for coins when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.searchForCoins("") } returns flowOf(
            CoinResponse.SUCCESS(mockk<SearchCoins>())
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.searchForCoins("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get exchanges when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchanges()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get exchanges when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getExchanges() } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchanges()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get exchanges when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getExchanges() } returns flowOf(
            CoinResponse.SUCCESS(listOf(mockk<Exchange>()))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchanges()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get derivative exchanges when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchanges(1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get derivative exchanges when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getDerivativeExchanges(1) } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchanges(1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get derivative exchanges when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getDerivativeExchanges(1) } returns flowOf(
            CoinResponse.SUCCESS(listOf(mockk<DerivativeExchange>()))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchanges(1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get exchange data when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchangeData("", 1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get exchange data when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getExchangeData("", 1) } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchangeData("", 1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get exchange data when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getExchangeData("", 1) } returns flowOf(
            CoinResponse.SUCCESS(mockk<ExchangeData>())
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getExchangeData("", 1)

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get derivative exchange data when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchangeData("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get derivative exchange data when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getDerivativeExchangeData("") } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchangeData("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get derivative exchange data when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getDerivativeExchangeData("") } returns flowOf(
            CoinResponse.SUCCESS(mockk<DerivativeExchangeData>())
        )
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.getDerivativeExchangeData("")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `get coin chart data when trying to load from server returns loading state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinMarketChart.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinChartData("", "")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
    }

    @Test
    fun `get coin chart data when trying to load from server returns error state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinChartData("", "") } returns flowOf(
            CoinResponse.ERROR(Exception("Error"))
        )
        target.coinMarketChart.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinChartData("", "")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.ERROR::class.java)
    }

    @Test
    fun `get coin chart data when trying to load from server returns success state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        every { mockRepository.getCoinChartData("", "") } returns flowOf(
            CoinResponse.SUCCESS(mockk<CoinChartData>())
        )
        target.coinMarketChart.observeForever {
            stateList.add(it)
        }
        // Action
        target.getCoinChartData("", "")

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(3)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[2]).isInstanceOf(CoinResponse.SUCCESS::class.java)
    }

    @Test
    fun `reset state returns default state`() {
        // AAA
        // Assign
        val stateList = mutableListOf<CoinResponse>()
        target.coinData.observeForever {
            stateList.add(it)
        }
        // Action
        target.resetState()

        // Assert
        assertThat(stateList).isNotEmpty()
        assertThat(stateList).hasSize(2)
        assertThat(stateList[0]).isInstanceOf(CoinResponse.LOADING::class.java)
        assertThat(stateList[1]).isInstanceOf(CoinResponse.DEFAULT::class.java)
    }

}