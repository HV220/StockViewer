package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

class Raw(
    @SerializedName("USD")
    val coinInfo: CoinInfo? = null
)
