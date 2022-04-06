package com.example.cryptohub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.databinding.DerivativeExchangeDetailsItemBinding
import com.example.cryptohub.model.derivatives.Ticker

class DerivativeDetailsAdapter(
    private val tickerList : MutableList<Ticker> = mutableListOf()
) : RecyclerView.Adapter<DerivativeDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DerivativeDetailsViewHolder {
        val view = DerivativeExchangeDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DerivativeDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DerivativeDetailsViewHolder, position: Int) {
        holder.bind(tickerList[position])
    }

    override fun getItemCount(): Int = tickerList.size

    fun updateTickers(newTickers : List<Ticker>) {
        tickerList.clear()
        tickerList.addAll(newTickers)
        notifyDataSetChanged()
    }
}

class DerivativeDetailsViewHolder(
    private val binding: DerivativeExchangeDetailsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : Ticker) {
        binding.pair.text = data.symbol

        val openInterest = "%,.2f".format(data.openInterestUsd)
        binding.openInterest.text = "$$openInterest"

        val priceDouble = data.convertedLast.usd.toDouble()
        val price = if ( priceDouble > 1) {
            "%,.2f".format(priceDouble)
        }
        else {
            "%,.6f".format(priceDouble)
        }
        binding.price.text = "$$price"
    }
}