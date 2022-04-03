package com.example.cryptohub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.databinding.ExchangeItemBinding
import com.example.cryptohub.model.exchanges.Exchange

class ExchangeAdapter(
    private val exchangeList : MutableList<Exchange> = mutableListOf(),
    private val onExchangeClicked: (Exchange) -> Unit
) : RecyclerView.Adapter<ExchangeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder {
        val view = ExchangeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ExchangeViewHolder(view, onExchangeClicked, parent.context)
    }

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bind(exchangeList[position])
    }

    override fun getItemCount(): Int = exchangeList.size

    fun updateExchanges(newExchange: List<Exchange>) {
        exchangeList.addAll(newExchange)
        notifyDataSetChanged()
    }
}

class ExchangeViewHolder(
    private val binding: ExchangeItemBinding,
    private val onExchangeClicked : (Exchange) -> Unit,
    private val context : Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(exchange: Exchange) {
        binding.exchangeName.text = exchange.name
        binding.rank.text = exchange.trustScoreRank.toString()

        val trustScore = exchange.trustScore
        binding.trustScore.text = "$trustScore/10"

        val volumeBTC = "%,.0f".format(exchange.tradeVolume24hBtc)
        binding.volume.text = "$volumeBTC"

        Glide.with(context)
            .load(exchange.image)
            .into(binding.image)
    }
}