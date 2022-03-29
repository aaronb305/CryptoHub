package com.example.cryptohub.model.search


import com.google.gson.annotations.SerializedName

data class SearchCoins(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("coins")
    val coins: List<Coin>,
    @SerializedName("exchanges")
    val exchanges: List<Exchange>,
    @SerializedName("icos")
    val icos: List<Any>,
    @SerializedName("nfts")
    val nfts: List<Nft>
)