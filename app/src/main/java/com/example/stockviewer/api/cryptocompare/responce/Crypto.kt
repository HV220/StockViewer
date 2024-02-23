package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

data class Crypto(
    @SerializedName("CoinInfo")
    private val coin: Coin? = null,
    @SerializedName("DISPLAY")
    private val display: DisplayCoin? = null,
)