package com.example.cryptohub.model.coinchartdata


import com.google.gson.annotations.SerializedName

data class CoinChartData(
    @SerializedName("market_caps")
    val marketCaps: List<List<Double>>,
    @SerializedName("prices")
    val prices: List<List<Double>>,
    @SerializedName("total_volumes")
    val totalVolumes: List<List<Double>>
)