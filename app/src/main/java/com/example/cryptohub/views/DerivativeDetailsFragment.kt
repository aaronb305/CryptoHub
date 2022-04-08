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
import com.example.cryptohub.adapter.DerivativeDetailsAdapter
import com.example.cryptohub.databinding.FragmentDerivativeDetailsBinding
import com.example.cryptohub.model.derivatives.DerivativeExchangeData
import com.example.cryptohub.model.exchanges.ExchangeData
import com.example.cryptohub.utils.CoinResponse

class DerivativeDetailsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentDerivativeDetailsBinding.inflate(layoutInflater)
    }

    private val detailsAdapter by lazy {
        DerivativeDetailsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            adapter = detailsAdapter
            layoutManager = mLayoutManager

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
        }

        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.coinId?.let {
                viewModel.getDerivativeExchangeData(it)
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
                    val details = it.response as DerivativeExchangeData
                    val tickers = details.tickers
                    detailsAdapter.updateTickers(tickers)
                    binding.name.text = details.name
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("derivatives details fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        viewModel.coinId?.let {
            viewModel.getDerivativeExchangeData(it)
        }
    }

    override fun onStop() {
        super.onStop()

        viewModel.coinData.removeObservers(viewLifecycleOwner)
    }
}