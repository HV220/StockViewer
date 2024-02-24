package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CryptoResponse(@SerializedName("Data") val cryptos: List<Crypto>? = null) : Serializable