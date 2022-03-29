package com.example.cryptohub.model.allcoins


import com.google.gson.annotations.SerializedName

data class CoinInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
)