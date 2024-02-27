package com.example.stockviewer.api.cryptocompare.responce

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DisplayCoin(
    @SerializedName("USD")
    @Embedded(prefix = "rate_")
    val rate: AboutCoinConvertedUsd? = null
): Serializable