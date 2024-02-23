package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

data class AboutCoinConvertedUsd(
    val nameRate: String = "USD",
    @SerializedName("PRICE")
    val price: String? = null,
    @SerializedName("LOWDAY")
    val lowDay: String? = null,
    @SerializedName("HIGHDAY")
    val HighDay: String? = null,
    @SerializedName("LASTMARKET")
    val lastBuy: String? = null,
)