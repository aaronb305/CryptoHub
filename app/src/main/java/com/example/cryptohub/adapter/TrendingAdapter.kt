package com.example.cryptohub.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.databinding.TrendingItemBinding
import com.example.cryptohub.model.trending.Coin
import com.example.cryptohub.model.trending.Item

class TrendingAdapter(
    private val trendingList : MutableList<Coin> = mutableListOf(),
    private val onTrendingClicked : (Coin) -> Unit
) : RecyclerView.Adapter<TrendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = TrendingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrendingViewHolder(view, onTrendingClicked, parent.context)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(trendingList[position])
    }

    override fun getItemCount(): Int = trendingList.size

    fun updateTrendingCoins(newCoins: List<Coin>) {
        trendingList.clear()
        trendingList.addAll(newCoins)
        notifyDataSetChanged()
    }
}

class TrendingViewHolder(
    private val binding : TrendingItemBinding,
    private val onTrendingClicked: (Coin) -> Unit,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin : Coin) {

        Log.d("trending adapter", "on bind called")

        binding.name.text = coin.item.name
        binding.rank.text = coin.item.marketCapRank.toString()
        binding.ticker.text = coin.item.symbol

        val price = if (coin.item.priceBtc < 1) {
            "%.6f".format(coin.item.priceBtc)
        }
        else {
            "%,.2f".format(coin.item.priceBtc)
        }
        binding.priceBTC.text = "\$${price}"

        binding.trendingItem.setOnClickListener {
            onTrendingClicked.invoke(coin)
        }

        Glide.with(context)
            .load(coin.item.small)
            .into(binding.picture)
    }
}