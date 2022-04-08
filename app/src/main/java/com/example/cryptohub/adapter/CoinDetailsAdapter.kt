package com.example.cryptohub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptohub.databinding.CoinDetailsItemBinding
import com.example.cryptohub.model.coindata.CoinData
import com.example.cryptohub.views.CoinDetailsFragment

class CoinDetailsAdapter(
    private val coinInfo : HashMap<String, Double> = hashMapOf()
) : RecyclerView.Adapter<CoinDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinDetailsViewHolder {
        val view = CoinDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CoinDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinDetailsViewHolder, position: Int) {
        holder.bind(coinInfo, coinInfo.keys.toList()[position])
    }

    override fun getItemCount(): Int = coinInfo.size

    fun updateDetails(detailsHashMap: HashMap<String, Double>) {
        coinInfo.clear()
        coinInfo.putAll(detailsHashMap)
        notifyDataSetChanged()
    }
}

class CoinDetailsViewHolder(
    private val binding : CoinDetailsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(map : HashMap<String, Double>, key : String) {
        binding.description.text = key
        binding.value.text = map[key]?.let {
            if (it > 1) {
                "%,.2f".format(it)
            }
            else {
                "%,.6f".format(it)
            }
        }
    }
}