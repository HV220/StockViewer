package com.example.stockviewer.api.cryptocompare.deserializer

import com.example.stockviewer.api.cryptocompare.ApiCommonInfo
import com.example.stockviewer.api.cryptocompare.responce.Coin
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CoinDeserializer : JsonDeserializer<Coin> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Coin {
        val jsonObject = json?.asJsonObject

        val imageUrl = jsonObject?.get("ImageUrl")?.asString

        val getName = jsonObject?.get("Name")?.asString

        val getAllPath =
            String.format("%s%s", ApiCommonInfo.ADDRESSES_SITE_CRYPTO.urlMainSite, imageUrl)

        return Coin(name = getName, imageUrl = getAllPath)
    }
}