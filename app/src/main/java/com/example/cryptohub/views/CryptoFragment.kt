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
            viewModel.coinId = it.id
            findNavController().navigate(R.id.detailsFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var pageNumber = 1

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            layoutManager = mLayoutManager
            adapter = cryptoAdapter

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (mLayoutManager.findLastVisibleItemPosition() == cryptoAdapter.itemCount - 1) {
                        pageNumber += 1
                        Log.d("crypto fragment", pageNumber.toString())
                        viewModel.getCoinsByMarketCap(pageNumber)
                    }
                }
            })
        }

        binding.swipeToRefresh.setOnRefreshListener {
            Log.d("crypto fragment", "listener called")
            viewModel.getCoinsByMarketCap()
            pageNumber = 1
            binding.swipeToRefresh.isRefreshing = true
            binding.recycler.visibility = View.GONE
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
                    val coins = it.response as List<CoinItem>
                    cryptoAdapter.updateCoins(coins)
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

        viewModel.getCoinsByMarketCap()
    }

    override fun onStop() {
        super.onStop()

        viewModel.coinData.removeObservers(viewLifecycleOwner)
    }

    companion object {
        fun newInstance() = CryptoFragment()
    }
}