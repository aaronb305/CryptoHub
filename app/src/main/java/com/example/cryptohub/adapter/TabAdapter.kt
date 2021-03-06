package com.example.cryptohub.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptohub.views.CryptoFragment
import com.example.cryptohub.views.DerivativesFragment
import com.example.cryptohub.views.ExchangesFragment


class TabAdapter(
    fm: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> CryptoFragment.newInstance()
            1 -> ExchangesFragment.newInstance()
            2 -> DerivativesFragment.newInstance()
            else -> CryptoFragment.newInstance()
        }
    }
}