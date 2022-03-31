package com.example.cryptohub.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cryptohub.R
import com.example.cryptohub.adapter.TrendingAdapter
import com.example.cryptohub.databinding.FragmentTrendingBinding
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.model.trending.Item
import com.example.cryptohub.model.trending.TrendingCoins
import com.example.cryptohub.utils.CoinResponse

class TrendingFragment : BaseFragment() {

    private val binding by lazy {
        FragmentTrendingBinding.inflate(layoutInflater)
    }

    private val trendingAdapter by lazy {
        TrendingAdapter(onTrendingClicked = {
            viewModel.coinId = it.id
            findNavController().navigate(R.id.action_trendingFragment_to_detailsFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.coinData.observe(viewLifecycleOwner) {
            when (it) {
                is CoinResponse.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is CoinResponse.SUCCESS<*> -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    val coins = it.response as List<Item>
                    trendingAdapter.updateTrendingCoins(coins)
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("crypto fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        viewModel.getTrendingCoins()
        // Inflate the layout for this fragment
        return binding.root
    }
}