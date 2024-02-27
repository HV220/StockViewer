package com.example.stockviewer.db.cryptocompare.stuctures

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.stockviewer.api.cryptocompare.responce.Crypto

@Dao
interface CryptoDao {
    @Query(value = "SELECT * FROM cryptos")
    fun getAllCryptos(): LiveData<List<Crypto>>

    @Insert
    fun insertCrypto(crypto: Crypto)

    @Delete
    fun deleteCrypto(crypto: Crypto)
}