package com.example.cryptohub.model.exchanges


import com.google.gson.annotations.SerializedName

data class ConvertedLast(
    @SerializedName("btc")
    val btc: Double,
    @SerializedName("eth")
    val eth: Double,
    @SerializedName("usd")
    val usd: Double
)