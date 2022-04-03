package com.example.cryptohub.model.exchanges


import com.google.gson.annotations.SerializedName

data class ExchangeData(
    @SerializedName("name")
    val name: String,
    @SerializedName("tickers")
    val tickers: List<Ticker>
)