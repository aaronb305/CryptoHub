package com.example.cryptohub.model.derivatives


import com.google.gson.annotations.SerializedName

data class Ticker(
    @SerializedName("base")
    val base: String,
    @SerializedName("bid_ask_spread")
    val bidAskSpread: Double,
    @SerializedName("contract_type")
    val contractType: String,
    @SerializedName("converted_last")
    val convertedLast: ConvertedLast,
    @SerializedName("converted_volume")
    val convertedVolume: ConvertedVolume,
    @SerializedName("expired_at")
    val expiredAt: Any,
    @SerializedName("funding_rate")
    val fundingRate: Double,
    @SerializedName("h24_percentage_change")
    val h24PercentageChange: Double,
    @SerializedName("h24_volume")
    val h24Volume: Double,
    @SerializedName("index")
    val index: Double,
    @SerializedName("index_basis_percentage")
    val indexBasisPercentage: Double,
    @SerializedName("last")
    val last: Double,
    @SerializedName("last_traded")
    val lastTraded: Int,
    @SerializedName("open_interest_usd")
    val openInterestUsd: Double,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("target")
    val target: String,
    @SerializedName("trade_url")
    val tradeUrl: String
)