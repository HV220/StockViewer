package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AboutCoinConvertedUsd(
    val nameRate: String = "USD",
    @SerializedName("PRICE")
    val price: String? = null,
    @SerializedName("LOWDAY")
    val lowDay: String? = null,
    @SerializedName("HIGHDAY")
    val highDay: String? = null,
    @SerializedName("LASTMARKET")
    val lastBuy: String? = null,
) : Serializable