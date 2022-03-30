package com.example.cryptohub.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.R
import com.example.cryptohub.databinding.CryptoItemBinding
import com.example.cryptohub.model.coinsbymarketcap.CoinItem
import java.lang.Math.abs
import java.util.logging.Logger

class CryptoAdapter(
    private val cryptoList: MutableList<CoinItem> = mutableListOf(),
    private val onCoinClicked : (CoinItem) -> Unit
) : RecyclerView.Adapter<CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = CryptoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CryptoViewHolder(view, parent.context, onCoinClicked)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(cryptoList[position])
    }

    override fun getItemCount(): Int = cryptoList.size

    fun updateCoins(newCoins: List<CoinItem>) {
        Log.d("adapter", "update coins called")
        val initialSize = cryptoList.size
        cryptoList.addAll(newCoins)
        notifyItemRangeInserted(initialSize, newCoins.size)
    }
}

class CryptoViewHolder(
    private val binding: CryptoItemBinding,
    private val context : Context,
    private val onCoinClicked : (CoinItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(coin: CoinItem) {
        Log.d("adapter", "on bind called")
        binding.ticker.text = coin.symbol.uppercase()
        binding.marketCapRank.text = coin.marketCapRank.toString()

        val price = if (coin.currentPrice < 1) {
            "%.6f".format(coin.currentPrice)
        }
        else {
            "%,.2f".format(coin.currentPrice)
        }
        binding.price.text = "\$${price}"

        val priceChange = "%.2f".format(abs(coin.priceChangePercentage24h))
        binding.change.text = "${priceChange}%"

        when {
            coin.priceChangePercentage24h < 0 -> {
                binding.change.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            coin.priceChangePercentage24h > 0 -> {
                binding.change.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            else -> {
                binding.change.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        val marketCap = "%,.0f".format(coin.marketCap)
        binding.marketCap.text = "\$${marketCap}"

        binding.coinItem.setOnClickListener {
            onCoinClicked.invoke(coin)
        }
    }
}