package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("CoinInfo")
    val coin: Coin? = null,
    @SerializedName("DISPLAY")
    val display: DisplayCoin? = null,
)