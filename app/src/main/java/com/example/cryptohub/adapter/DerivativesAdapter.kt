package com.example.cryptohub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptohub.databinding.DerivativesItemBinding
import com.example.cryptohub.model.derivatives.DerivativeExchange

class DerivativesAdapter(
    private val derivativesList : MutableList<DerivativeExchange> = mutableListOf(),
    private val onDerivativeClicked : (DerivativeExchange) -> Unit
) : RecyclerView.Adapter<DerivativesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DerivativesViewHolder {
        val view = DerivativesItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DerivativesViewHolder(view, onDerivativeClicked, parent.context)
    }

    override fun onBindViewHolder(holder: DerivativesViewHolder, position: Int) {
        holder.bind(derivativesList[position])
    }

    override fun getItemCount(): Int = derivativesList.size

    fun updateDerivatives(newDerivatives : List<DerivativeExchange>) {
        derivativesList.addAll(newDerivatives)
        notifyDataSetChanged()
    }
}

class DerivativesViewHolder(
    private val binding : DerivativesItemBinding,
    private val onDerivativeClicked: (DerivativeExchange) -> Unit,
    private val context : Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(exchange: DerivativeExchange) {
        binding.derivativeName.text = exchange.name

        val volumeBTC = "%,.2f".format(exchange.tradeVolume24hBtc.toDouble())
        binding.volume.text = volumeBTC

        val oiBTC = "%,.2f".format(exchange.openInterestBtc)
        binding.openInterest.text = oiBTC

        binding.derivativesItem.setOnClickListener {
            onDerivativeClicked.invoke(exchange)
        }

        Glide.with(context)
            .load(exchange.image)
            .into(binding.symbol)
    }
}