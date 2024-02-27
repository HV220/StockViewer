package com.example.stockviewer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.stockviewer.api.ApiFactory
import com.example.stockviewer.api.configurator.RetrofitDefaultConfigurator
import com.example.stockviewer.api.cryptocompare.deserializer.CoinDeserializer
import com.example.stockviewer.api.cryptocompare.deserializer.CoinInfoDeserializer
import com.example.stockviewer.api.cryptocompare.responce.Coin
import com.example.stockviewer.api.cryptocompare.responce.CoinInfo
import com.example.stockviewer.api.cryptocompare.responce.Crypto
import com.example.stockviewer.api.cryptocompare.service.TopList24hVolumeService
import com.example.stockviewer.db.cryptocompare.stuctures.CryptoDatabase
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinsViewModel(
    application: Application,
    private val db: CryptoDatabase? = CryptoDatabase.getInstance(application)
) : AndroidViewModel(application) {
    companion object {
        const val URL: String = "https://min-api.cryptocompare.com/"
        const val RATE: String = "USD"
        const val LIMIT_ELEMENTS: Int = 50
    }

    private var page = 0
    private val cryptosData = MutableLiveData<List<Crypto>>()
    private val isInfoCoinsLoading = MutableLiveData(false)
    private val error = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()


    fun getInfoAboutCoins(): LiveData<List<Crypto>> {
        return cryptosData
    }

    fun getError(): LiveData<Boolean> {
        return error
    }

    fun getInfoCoinsLoading(): LiveData<Boolean> {
        return isInfoCoinsLoading
    }

    fun loadInfoAboutCoins() {
        error.value = false
        val gson = GsonBuilder()
            .registerTypeAdapter(CoinInfo::class.java, CoinInfoDeserializer())
            .registerTypeAdapter(Coin::class.java, CoinDeserializer())
            .create()

        val disposable: Disposable = ApiFactory
            .getApiService<TopList24hVolumeService>(
                URL, RetrofitDefaultConfigurator(), gson
            )
            .loadTop24Crypto(
                LIMIT_ELEMENTS, page, RATE
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isInfoCoinsLoading.value = true }
            .doAfterTerminate { isInfoCoinsLoading.value = false }
            .subscribe({ successResult ->
                val currentData = cryptosData
                    .value?.toMutableList() ?: mutableListOf()

                successResult
                    .cryptos?.let { newList ->
                        currentData.addAll(newList)
                    }

                cryptosData.postValue(currentData)

                page++

            }, { error.postValue(true) }

            )
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}