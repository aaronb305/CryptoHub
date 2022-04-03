package com.example.cryptohub.model.derivatives


import com.google.gson.annotations.SerializedName

data class DerivativeExchange(
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("number_of_futures_pairs")
    val numberOfFuturesPairs: Int,
    @SerializedName("number_of_perpetual_pairs")
    val numberOfPerpetualPairs: Int,
    @SerializedName("open_interest_btc")
    val openInterestBtc: Double,
    @SerializedName("trade_volume_24h_btc")
    val tradeVolume24hBtc: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("year_established")
    val yearEstablished: Int
)