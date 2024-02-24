package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Raw(
    @SerializedName("USD")
    val coinInfo: CoinInfo? = null
): Serializable
