package com.example.stockviewer.api.cryptocompare.responce

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "cryptos")
data class Crypto(
    @PrimaryKey
    val id: Int? = null,
    @SerializedName("CoinInfo")
    @Embedded(prefix = "coin_")
    val coin: Coin? = null,
    @SerializedName("DISPLAY")
    @Embedded(prefix = "display_")
    val display: DisplayCoin? = null,
    @SerializedName("RAW")
    @Embedded(prefix = "raw_")
    val raw: Raw? = null
) : Serializable