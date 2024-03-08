package com.example.stockviewer.api.cryptocompare.responce

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Raw(
    @SerializedName("USD")
    @Embedded(prefix = "coinInfo_")
    val coinInfo: CoinInfo? = null
): Serializable
