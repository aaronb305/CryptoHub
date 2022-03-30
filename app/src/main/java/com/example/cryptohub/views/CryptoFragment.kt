package com.example.cryptohub.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.R
import com.example.cryptohub.adapter.CryptoAdapter
import com.example.cryptohub.databinding.FragmentCryptoBinding
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import com.example.cryptohub.utils.CoinResponse

class CryptoFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCryptoBinding.inflate(layoutInflater)
    }

    private val cryptoAdapter by lazy {
        CryptoAdapter(onCoinClicked = {
            viewModel.coin = it
            findNavController().navigate(R.id.action_cryptocurrency_to_detailsFragment2)
        })
    }

    var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false)
            layoutManager = mLayoutManager
            adapter = cryptoAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (mLayoutManager.findLastVisibleItemPosition() == cryptoAdapter.itemCount - 1) {
                        pageNumber += 1
                        viewModel.getCoinsByMarketCap(pageNumber)
                    }
                }
            })
        }

        viewModel.coinData.observe(viewLifecycleOwner) {
            when (it) {
                is CoinResponse.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                }
                is CoinResponse.SUCCESS<*> -> {
                    binding.loadingBar.visibility = View.GONE
                    val coins = it.response as List<CoinItem>
                    cryptoAdapter.updateCoins(coins)
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    Log.e("crypto fragment", it.error.localizedMessage)
                }
            }
        }

        viewModel.getCoinsByMarketCap(1)

        // Inflate the layout for this fragment
        return binding.root
    }
}