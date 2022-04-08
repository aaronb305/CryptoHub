package com.example.cryptohub.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptohub.R
import com.example.cryptohub.adapter.CoinDetailsAdapter
import com.example.cryptohub.databinding.FragmentDetailsBinding
import com.example.cryptohub.model.coinchartdata.CoinChartData
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.utils.CoinResponse

class CoinDetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val coinId by lazy {
        viewModel.coinId
    }

    private val detailsAdapter by lazy {
        CoinDetailsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            layoutManager = mLayoutManager
            adapter = detailsAdapter
            setHasFixedSize(true)

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }

        binding.swipeToRefresh.setOnRefreshListener {
            Log.d("coin details fragment", "listener called")
            coinId?.let { id ->
                viewModel.getCoinById(id)
            }
            binding.swipeToRefresh.isRefreshing = true
            binding.recycler.visibility = View.GONE
        }

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.coinData.observe(viewLifecycleOwner) {
            when (it) {
                is CoinResponse.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is CoinResponse.SUCCESS<*> -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    binding.swipeToRefresh.isRefreshing = false
                    val details = it.response as CoinData
                    binding.coin.text = details.name
                    val detailsHashMap = createHashMap(details)
                    detailsAdapter.updateDetails(detailsHashMap)
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("details fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        viewModel.coinMarketChart.observe(viewLifecycleOwner) {
            when (it) {
                is CoinResponse.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is CoinResponse.SUCCESS<*> -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    val chartData = it.response as CoinChartData
                    val prices = chartData.prices
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("details fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        coinId?.let { id ->
            viewModel.getCoinById(id)
            viewModel.getCoinChartData(id, "30")
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.coinData.removeObservers(viewLifecycleOwner)
        viewModel.coinMarketChart.removeObservers(viewLifecycleOwner)
    }

    private fun  createHashMap(details: CoinData) : HashMap<String, Double> {
        return hashMapOf(
            "Market Cap Rank" to details.marketCapRank.toDouble(),
            "Market Cap" to details.marketData.marketCap.usd.toDouble(),
            "Fully Diluted Valuation" to details.marketData.fullyDilutedValuation.usd.toDouble(),
            "Trading Volume" to details.marketData.totalVolume.usd.toDouble(),
            "24H High" to details.marketData.high24h.usd.toDouble(),
            "24H Low" to details.marketData.low24h.usd.toDouble(),
            "Circulating Supply" to details.marketData.circulatingSupply.toDouble(),
            "Max Supply" to details.marketData.maxSupply.toDouble(),
            "Total Supply" to details.marketData.totalSupply.toDouble(),
            "All-Time High" to details.marketData.ath.usd.toDouble(),
            "All-Time Low" to details.marketData.atl.usd
        )
    }
}