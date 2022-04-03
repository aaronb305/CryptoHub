package com.example.cryptohub.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptohub.R
import com.example.cryptohub.adapter.SearchAdapter
import com.example.cryptohub.databinding.FragmentSearchBinding
import com.example.cryptohub.model.search.Coin
import com.example.cryptohub.model.search.SearchCoins
import com.example.cryptohub.utils.CoinResponse
import dagger.hilt.android.AndroidEntryPoint

class SearchFragment : BaseFragment() {

    private val binding by lazy {
        FragmentSearchBinding.inflate(layoutInflater)
    }

    private val searchAdapter by lazy {
        SearchAdapter(onCoinClicked = {
            viewModel.coinId = it.id
            findNavController().navigate(R.id.detailsFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            layoutManager = mLayoutManager
            adapter = searchAdapter
        }

        viewModel.coinData.observe(viewLifecycleOwner) {
            when (it) {
                is CoinResponse.LOADING -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                }
                is CoinResponse.SUCCESS<*> -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                    val searchResults = it.response as SearchCoins
                    val coins = searchResults.coins
                    searchAdapter.updateCoins(coins)
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.recycler.visibility = View.GONE
                    Log.e("search fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        binding.searchField.addTextChangedListener {
            if (it.toString().isNullOrEmpty()) {
                binding.recycler.visibility = View.GONE
            }
            else {
                binding.recycler.visibility = View.VISIBLE
                viewModel.searchForCoins(it.toString())
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}