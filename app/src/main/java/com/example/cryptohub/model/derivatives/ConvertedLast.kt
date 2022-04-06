package com.example.cryptohub.model.derivatives


import com.google.gson.annotations.SerializedName

data class ConvertedLast(
    @SerializedName("btc")
    val btc: String,
    @SerializedName("eth")
    val eth: String,
    @SerializedName("usd")
    val usd: String
)