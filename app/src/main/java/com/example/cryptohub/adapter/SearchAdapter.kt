package com.example.cryptohub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.databinding.SearchItemBinding
import com.example.cryptohub.model.search.Coin

class SearchAdapter(
    private val coinList : MutableList<Coin> = mutableListOf(),
    private val onCoinClicked : (Coin) -> Unit
) : RecyclerView.Adapter<SearchViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = SearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchViewHolder(view, onCoinClicked)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(coinList[position])
    }

    override fun getItemCount(): Int = coinList.size

    fun updateCoins(newCoins : List<Coin>) {
        coinList.clear()
        coinList.addAll(newCoins)
        notifyDataSetChanged()
    }
}

class SearchViewHolder(
    private val binding : SearchItemBinding,
    private val onCoinClicked : (Coin) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin : Coin) {
        binding.name.text = coin.name
        binding.symbol.text = coin.symbol
        val rank = coin.marketCapRank.toString()
        binding.marketCapRank.text = "#$rank"

        binding.searchItem.setOnClickListener {
            onCoinClicked.invoke(coin)
        }
    }
}