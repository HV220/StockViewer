package com.example.stockviewer.db.cryptocompare.stuctures

import android.app.Application
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.stockviewer.api.cryptocompare.responce.Crypto


@Database(entities = [Crypto::class], version = 1, exportSchema = false)
abstract class CryptoDatabase() : RoomDatabase() {
    abstract fun cryptoDao(): CryptoDao

    companion object {

        @Volatile
        private var instance: CryptoDatabase? = null
        private const val DB_NAME = "crypto.db"

        fun getInstance(application: Application): CryptoDatabase = instance ?: synchronized(this) {
            instance ?: databaseBuilder(
                application,
                CryptoDatabase::class.java,
                DB_NAME
            ).build().also { instance = it }

        }
    }
}