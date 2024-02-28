package com.example.stockviewer.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
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
import com.example.stockviewer.db.cryptocompare.stuctures.CryptoDao
import com.example.stockviewer.db.cryptocompare.stuctures.CryptoDatabase
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CoinsViewModel(
    private val application: Application,
    private val db: CryptoDao = CryptoDatabase.getInstance(application).cryptoDao()

) : AndroidViewModel(application) {
    companion object {
        const val URL: String = "https://min-api.cryptocompare.com/"
        const val RATE: String = "USD"
        const val LIMIT_ELEMENTS: Int = 20
    }

    private var page = 0
    private val isInfoCoinsLoading = MutableLiveData(false)
    private val errorGetDataApi = MutableLiveData(false)
    private val isNetworkAvailable = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()


    fun getInfoAboutCoins(): LiveData<List<Crypto>> {
        return db.getAllCryptos()
    }

    fun getError(): LiveData<Boolean> {
        return errorGetDataApi
    }

    fun getInfoCoinsLoading(): LiveData<Boolean> {
        return isInfoCoinsLoading
    }

    fun getNetworkAvailable(): LiveData<Boolean> {
        return isNetworkAvailable
    }

    fun loadInfoAboutCoins() {
        errorGetDataApi.value = false

        if (isNetworkAvailable.value == false) return

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

                successResult
                    .cryptos?.let { newList ->
                        insertDataDb(newList)

                        page++
                    }
            }, {
                errorGetDataApi.postValue(true)
                page = 0
            }

            )
        compositeDisposable.add(disposable)
    }

    fun registerNetworkCallback(context: Context) {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val request = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(
            request,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    page = 0
                    isNetworkAvailable.postValue(true)
                }

                override fun onLost(network: Network) {
                    page = 0
                    isNetworkAvailable.postValue(false)
                }
            })
    }

    fun deleteAllDataDb() {
        val deleteDisposable = db.deleteAllCryptos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(deleteDisposable)
    }

    private fun insertDataDb(data: List<Crypto>) {
        val insertDisposable: Disposable =
            db.insertAllCryptos(data).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { isInfoCoinsLoading.value = true }
                .doAfterTerminate { isInfoCoinsLoading.value = false }
                .subscribe()
        compositeDisposable.add(insertDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}