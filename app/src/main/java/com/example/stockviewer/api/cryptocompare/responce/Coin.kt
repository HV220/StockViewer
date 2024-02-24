package com.example.stockviewer.api.cryptocompare.responce

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coin(
    @SerializedName("Name") val name: String? = null,
    @SerializedName("ImageUrl") val imageUrl: String? = null
) : Serializable