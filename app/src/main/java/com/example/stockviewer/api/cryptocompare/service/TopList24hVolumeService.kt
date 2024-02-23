package com.example.stockviewer.api.cryptocompare.service

import com.example.stockviewer.api.cryptocompare.responce.CryptoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TopList24hVolumeService {
    @GET("data/top/totalvolfull")
    fun loadTop24Crypto(
        @Query("limit") limit: Int,
        @Query("tsym") tsym: String
    ): Single<CryptoResponse>
}