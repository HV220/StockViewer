package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CoinInfo(
    @SerializedName("LASTUPDATE")
    val timeLastUpdate: String? = null
) : Serializable
