package com.example.stockviewer.db.cryptocompare.stuctures

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.stockviewer.api.cryptocompare.responce.Crypto
import io.reactivex.rxjava3.core.Completable

@Dao
interface CryptoDao {
    @Query(value = "SELECT * FROM cryptos")
    fun getAllCryptos(): LiveData<List<Crypto>>

    @Insert
    fun insertAllCryptos(cryptos: List<Crypto>) : Completable

    @Delete
    fun deleteCrypto(crypto: Crypto) : Completable
}