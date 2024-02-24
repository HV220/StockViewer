package com.example.stockviewer.api.cryptocompare.deserializer

import com.example.stockviewer.api.cryptocompare.responce.CoinInfo
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.lang.reflect.Type

class CoinInfoDeserializer : JsonDeserializer<CoinInfo> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): CoinInfo {
        val jsonObject = json?.asJsonObject

        val timeLastUpdate = jsonObject?.get("LASTUPDATE")?.asString
        val convertedTimeLastUpdate = timeLastUpdate?.toLongOrNull()?.let {
            convertTimestampToDateTime(it)
        }

        return CoinInfo(timeLastUpdate = convertedTimeLastUpdate)
    }

    private fun convertTimestampToDateTime(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }
}