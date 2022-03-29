package com.example.cryptohub.model.trending


import com.google.gson.annotations.SerializedName

data class TrendingCoins(
    @SerializedName("coins")
    val coins: List<Coin>,
    @SerializedName("exchanges")
    val exchanges: List<Any>
)