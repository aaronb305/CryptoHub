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
import com.example.cryptohub.adapter.ExchangeDetailsAdapter
import com.example.cryptohub.databinding.FragmentExchangeDetailsBinding
import com.example.cryptohub.model.exchanges.Exchange
import com.example.cryptohub.model.exchanges.ExchangeData
import com.example.cryptohub.utils.CoinResponse

class ExchangeDetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentExchangeDetailsBinding.inflate(layoutInflater)
    }

    private val detailsAdapter by lazy {
        ExchangeDetailsAdapter()
    }

    var pageNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            adapter = detailsAdapter
            layoutManager = mLayoutManager

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (mLayoutManager.findLastVisibleItemPosition() == detailsAdapter.itemCount - 1) {
                        pageNumber += 1
                        Log.d("crypto fragment", pageNumber.toString())
                        viewModel.coinId?.let {
                            viewModel.getExchangeData(it, pageNumber)
                        }
                    }
                }
            })
        }

        binding.swipeToRefresh.setOnRefreshListener {
            pageNumber = 1
            viewModel.coinId?.let {
                viewModel.getExchangeData(it, pageNumber)
            }
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
                    val details = it.response as ExchangeData
                    val tickers = details.tickers
                    detailsAdapter.updateDetails(tickers)
                    binding.exchangeName.text = details.name
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("exchange details fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        viewModel.coinId?.let {
            viewModel.getExchangeData(it, pageNumber)
        }
    }
}