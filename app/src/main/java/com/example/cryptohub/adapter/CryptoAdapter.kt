package com.example.cryptohub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.databinding.CryptoItemBinding
import com.example.cryptohub.model.coindata.CoinData

class CryptoAdapter(
    private val cryptoList: MutableList<CoinData> = mutableListOf(),
    private val onCoinClicked : (CoinData) -> Unit
) : RecyclerView.Adapter<CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = CryptoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(view, onCoinClicked)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int = cryptoList.size

    fun updateCoins(newCoins: List<CoinData>) {
        cryptoList.clear()
        cryptoList.addAll(newCoins)
        notifyDataSetChanged()
    }
}

class CryptoViewHolder(
    private val binding: CryptoItemBinding,
    private val onCoinClicked : (CoinData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: CoinData) {
        binding.ticker.text = coin.symbol
        binding.marketCapRank.text = coin.marketCapRank.toString()
        binding.price.text = coin.marketData.currentPrice.usd.toString()
        binding.change.text = coin.marketData.priceChange24h.toString()
        binding.marketCap.text = coin.marketData.marketCap.usd.toString()

        binding.coinItem.setOnClickListener {
            onCoinClicked.invoke(coin)
        }
    }
}