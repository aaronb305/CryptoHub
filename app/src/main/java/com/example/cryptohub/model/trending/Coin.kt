package com.example.cryptohub.model.trending


import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("item")
    val item: Item
)