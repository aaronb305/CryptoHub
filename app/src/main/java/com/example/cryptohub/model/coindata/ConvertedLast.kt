package com.example.cryptohub.model.coindata


import com.google.gson.annotations.SerializedName

data class ConvertedLast(
    @SerializedName("btc")
    val btc: Int,
    @SerializedName("eth")
    val eth: Double,
    @SerializedName("usd")
    val usd: Int
)