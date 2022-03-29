package com.example.cryptohub.model.coindata


import com.google.gson.annotations.SerializedName

data class CodeAdditionsDeletions4Weeks(
    @SerializedName("additions")
    val additions: Int,
    @SerializedName("deletions")
    val deletions: Int
)