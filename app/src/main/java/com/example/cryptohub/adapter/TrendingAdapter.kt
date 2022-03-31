package com.example.cryptohub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.databinding.TrendingItemBinding
import com.example.cryptohub.model.trending.Item
import com.example.cryptohub.model.trending.TrendingCoins

class TrendingAdapter(
    private val trendingList : MutableList<Item> = mutableListOf(),
    private val onTrendingClicked : (Item) -> Unit
) : RecyclerView.Adapter<TrendingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = TrendingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrendingViewHolder(view, onTrendingClicked)
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        holder.bind(trendingList[position])
    }

    override fun getItemCount(): Int = trendingList.size

    fun updateTrendingCoins(newCoins: List<Item>) {
        trendingList.clear()
        trendingList.addAll(newCoins)
        notifyDataSetChanged()
    }
}

class TrendingViewHolder(
    private val binding : TrendingItemBinding,
    private val onTrendingClicked: (Item) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin : Item) {

        binding.trendingItem.setOnClickListener {
            onTrendingClicked.invoke(coin)
        }
    }
}