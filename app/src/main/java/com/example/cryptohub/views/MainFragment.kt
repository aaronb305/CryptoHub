package com.example.cryptohub.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptohub.R
import com.example.cryptohub.adapter.CryptoAdapter
import com.example.cryptohub.adapter.TabAdapter
import com.example.cryptohub.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : BaseFragment() {

    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("main fragment", view.findNavController().graph.startDestDisplayName)
//        binding.navBarHomeScreen.setupWithNavController(
//            requireActivity().findNavController(R.id.homeContainer)
//        )

        binding.homeContainer.apply {
            adapter = TabAdapter(childFragmentManager, lifecycle)
            isSaveEnabled = false
        }

        TabLayoutMediator(binding.tabLayout, binding.homeContainer) { tab, position ->
            when(position) {
                0 -> {
                    tab.text = "Cryptocurrency"
                    tab.icon = getDrawable(requireContext(), R.drawable.bitcoin)
                }
                1 -> {
                    tab.text = "Exchanges"
                    tab.icon = getDrawable(requireContext(), R.drawable.exchange)
                }
                else -> {
                    tab.text = "Derivatives"
                    tab.icon = getDrawable(requireContext(), R.drawable.ic_baseline_arrow_forward_24)
                }
            }
        }.attach()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }
}