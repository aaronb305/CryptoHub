package com.example.cryptohub.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptohub.R
import com.example.cryptohub.databinding.FragmentTrendingBinding

class TrendingFragment : BaseFragment() {

    private val binding by lazy {
        FragmentTrendingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }
}