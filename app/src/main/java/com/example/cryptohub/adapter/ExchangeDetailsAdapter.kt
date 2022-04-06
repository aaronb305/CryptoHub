package com.example.cryptohub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.databinding.ExchangeDetailsItemBinding
import com.example.cryptohub.model.exchanges.Ticker

class ExchangeDetailsAdapter(
    private val exchangeList : MutableList<Ticker> = mutableListOf()
) : RecyclerView.Adapter<ExchangeDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeDetailsViewHolder {
        val view = ExchangeDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExchangeDetailsViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ExchangeDetailsViewHolder, position: Int) {
        holder.bind(exchangeList[position])
    }

    override fun getItemCount(): Int = exchangeList.size

    fun updateDetails(tickers: List<Ticker>) {
        exchangeList.addAll(tickers)
        notifyDataSetChanged()
    }

}
class ExchangeDetailsViewHolder(
    private val binding: ExchangeDetailsItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data : Ticker) {
        binding.firstPair.text = data.base
        binding.secondPair.text = data.target

        val price = if (data.convertedLast.usd > 1) {
            "%,.2f".format(data.convertedLast.usd)
        }
        else {
            "%,.6f".format(data.convertedLast.usd)
        }
        binding.price.text = "$$price"



        val volume = data.convertedVolume.usd.toDouble()
        binding.volume.text = "%,.2f".format(volume)

//        Glide.with(context)
    }
}