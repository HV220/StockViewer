package com.example.stockviewer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.MutableLiveData
import com.example.stockviewer.api.ApiFactory
import com.example.stockviewer.api.configurator.RetrofitDefaultConfigurator
import com.example.stockviewer.api.cryptocompare.responce.CryptoResponse
import com.example.stockviewer.api.cryptocompare.service.TopList24hVolumeService
import com.example.stockviewer.databinding.ActivityMainBinding
import com.jakewharton.threetenabp.AndroidThreeTen
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var num = MutableLiveData<Int>(0)
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        val timestamp = 1708687042L
        val dateTime = convertTimestampToDateTime(timestamp)
        Log.e("MainActivity", dateTime)

        val disposable = ApiFactory.getApiService<TopList24hVolumeService>(
            "https://min-api.cryptocompare.com/",
            RetrofitDefaultConfigurator()
        )
            .loadTop24Crypto(10, "USD")
            .subscribe(
                { cryptoResponse ->
                    // This lambda is called on success
                    cryptoResponse.cryptos?.forEach {
                        Log.d("MainActivity", it.toString())
                    }
                },
                { throwable ->
                    // This lambda is called on error
                    Log.e("MainActivity", "Error fetching crypto data", throwable)
                }
            )
        var test = CryptoResponse()
    }


    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // Убедитесь, что остановили таймер
    }

    fun convertTimestampToDateTime(timestamp: Long): String {
        val instant = Instant.ofEpochSecond(timestamp)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }
}
