package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName

class CoinInfo(
    @SerializedName("LASTUPDATE")
    val timeLastUpdate: String? = null
)
