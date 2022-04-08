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
import com.example.cryptohub.adapter.ExchangeAdapter
import com.example.cryptohub.databinding.FragmentExchangesBinding
import com.example.cryptohub.model.exchanges.Exchange
import com.example.cryptohub.utils.CoinResponse

class ExchangesFragment : BaseFragment() {

    private val binding by lazy {
        FragmentExchangesBinding.inflate(layoutInflater)
    }

    private val exchangeAdapter by lazy {
        ExchangeAdapter(onExchangeClicked = {
            viewModel.coinId = it.id
            findNavController().navigate(R.id.exchangeDetailsFragment)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("exchange fragment", "on create called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("exchange fragment", "on create view called")

        var pageNumber = 1

        binding.recycler.apply {
            val mLayoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL, false
            )
            layoutManager = mLayoutManager
            adapter = exchangeAdapter

            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (mLayoutManager.findLastVisibleItemPosition() == exchangeAdapter.itemCount - 1) {
                        pageNumber += 1
                        Log.d("exchange fragment", pageNumber.toString())
                        viewModel.getExchanges(pageNumber)
                    }
                }
            })
        }

        binding.swipeToRefresh.setOnRefreshListener {
            Log.d("exchange fragment", "listener called")
            viewModel.getExchanges()
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
                    val exchanges = it.response as List<Exchange>
                    exchangeAdapter.updateExchanges(exchanges)
                    viewModel.resetState()
                }
                is CoinResponse.ERROR -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.swipeToRefresh.isRefreshing = false
                    Log.e("exchange fragment", it.error.localizedMessage)
                    viewModel.resetState()
                }
            }
        }

        viewModel.getExchanges()
    }

    override fun onStop() {
        super.onStop()

        viewModel.coinData.removeObservers(viewLifecycleOwner)
    }

    companion object {
        fun newInstance() = ExchangesFragment()
    }
}