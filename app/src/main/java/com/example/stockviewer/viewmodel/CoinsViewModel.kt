package com.example.stockviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stockviewer.api.ApiFactory
import com.example.stockviewer.api.configurator.RetrofitDefaultConfigurator
import com.example.stockviewer.api.cryptocompare.responce.Crypto
import com.example.stockviewer.api.cryptocompare.service.TopList24hVolumeService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class CoinsViewModel : ViewModel() {
    companion object {
        const val URL: String = "https://min-api.cryptocompare.com/"
        const val RATE: String = "USD"
        const val LIMIT_ELEMENTS: Int = 10
    }

    private var page = 1
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

    private fun loadInfoAboutCoins() {

        error.value = false
        val disposable: Disposable = ApiFactory.getApiService<TopList24hVolumeService>(
                URL, RetrofitDefaultConfigurator()
            ).loadTop24Crypto(
                LIMIT_ELEMENTS, page, RATE
            ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isInfoCoinsLoading.value = true }
            .doAfterTerminate { isInfoCoinsLoading.value = false }.subscribe({ successResult ->
                val currentData = cryptosData.value?.toMutableList() ?: mutableListOf()
                successResult.cryptos?.let { newList ->
                    currentData.addAll(newList)
                }
                cryptosData.postValue(currentData)
                page++
            }, { error.postValue(true) }

            )
        compositeDisposable.add(disposable)
    }

    private fun convertTimestampToDateTime(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}