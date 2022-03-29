package com.example.cryptohub.model.coindata


import com.google.gson.annotations.SerializedName

data class Localization(
    @SerializedName("ar")
    val ar: String,
    @SerializedName("de")
    val de: String,
    @SerializedName("en")
    val en: String,
    @SerializedName("es")
    val es: String,
    @SerializedName("fr")
    val fr: String,
    @SerializedName("hu")
    val hu: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("it")
    val `it`: String,
    @SerializedName("ja")
    val ja: String,
    @SerializedName("ko")
    val ko: String,
    @SerializedName("nl")
    val nl: String,
    @SerializedName("pl")
    val pl: String,
    @SerializedName("pt")
    val pt: String,
    @SerializedName("ro")
    val ro: String,
    @SerializedName("ru")
    val ru: String,
    @SerializedName("sv")
    val sv: String,
    @SerializedName("th")
    val th: String,
    @SerializedName("tr")
    val tr: String,
    @SerializedName("vi")
    val vi: String,
    @SerializedName("zh")
    val zh: String,
    @SerializedName("zh-tw")
    val zhTw: String
)