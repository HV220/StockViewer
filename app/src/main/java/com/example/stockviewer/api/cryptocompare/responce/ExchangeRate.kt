package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

data class DisplayCoin(
    @SerializedName("USD")
    val rate: AboutCoinConvertedUsd? = null
)