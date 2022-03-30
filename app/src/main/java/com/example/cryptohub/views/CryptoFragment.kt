package com.example.cryptohub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cryptohub.R
import com.example.cryptohub.adapter.CryptoAdapter
import com.example.cryptohub.databinding.FragmentCryptoBinding

class CryptoFragment : BaseFragment() {

    private val binding by lazy {
        FragmentCryptoBinding.inflate(layoutInflater)
    }

    private val cryptoAdapter by lazy {
        CryptoAdapter(onCoinClicked = {
            viewModel.coin = it
            findNavController().navigate(R.id.detailsFragment)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return binding.root
    }
}