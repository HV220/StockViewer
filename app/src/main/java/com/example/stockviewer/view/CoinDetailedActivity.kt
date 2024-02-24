package com.example.stockviewer.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.stockviewer.R
import com.example.stockviewer.adapter.cryptocompare.CoinDetailedAdapter
import com.example.stockviewer.api.cryptocompare.responce.Crypto
import com.squareup.picasso.Picasso
import java.io.Serializable

class CoinDetailedActivity : ComponentActivity() {
    private lateinit var adapter: CoinDetailedAdapter
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var recycler: RecyclerView


    companion object {
        private const val COIN_DATA: String = "COIN_DATA"
        private const val PRICE = "price: "
        private const val LOW_DAY = "low price of day: "
        private const val HIGH_DAY = "high price of day: "
        private const val LAST_DAY = "last buy of day: "
        private const val TIME_UPDATED = "last time updated: "

        fun createIntent(context: Context, crypto: Crypto): Intent {
            val intent = Intent(context, CoinDetailedActivity::class.java)
            intent.putExtra(COIN_DATA, crypto)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detailed)

        setContentView(R.layout.activity_coin_detailed)

        adapter = CoinDetailedAdapter()
        initViews()

        recycler.adapter = adapter

        val cryptoResult = intent.serializable<Crypto>(COIN_DATA)

        val cryptoTitle = mutableListOf<String>()
        val cryptoInfo = mutableListOf<String>()

        cryptoTitle.add(PRICE)
        cryptoTitle.add(LOW_DAY)
        cryptoTitle.add(HIGH_DAY)
        cryptoTitle.add(LAST_DAY)
        cryptoTitle.add(TIME_UPDATED)

        cryptoInfo.add(cryptoResult?.display?.rate?.price.toString())
        cryptoInfo.add(cryptoResult?.display?.rate?.lowDay.toString())
        cryptoInfo.add(cryptoResult?.display?.rate?.highDay.toString())
        cryptoInfo.add(cryptoResult?.display?.rate?.lastBuy.toString())
        cryptoInfo.add(cryptoResult?.raw?.coinInfo?.timeLastUpdate.toString())

        adapter.nameFolder = cryptoTitle
        adapter.infoFolder = cryptoInfo

        Picasso.get().load(cryptoResult?.coin?.imageUrl).into(image)
        title.text = String.format(
            "%s / %s",
            cryptoResult?.coin?.name,
            cryptoResult?.display?.rate?.nameRate
        )
    }

    private inline fun <reified T : java.io.Serializable> Bundle.serializable(key: String): T? =
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(
                key,
                T::class.java
            )

            else -> @Suppress("DEPRECATION") getSerializable(key) as? T
        }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(
            key,
            T::class.java
        )

        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    private fun initViews() {
        title = findViewById(R.id.titleCoin)
        image = findViewById(R.id.coinImage)
        recycler = findViewById(R.id.aboutCoinInfo)
    }
}