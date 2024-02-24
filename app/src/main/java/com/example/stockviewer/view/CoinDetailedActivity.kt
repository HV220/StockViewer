package com.example.stockviewer.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.stockviewer.R
import com.example.stockviewer.api.cryptocompare.responce.Crypto

class CoinDetailedActivity : ComponentActivity() {
    companion object {
        private const val COIN_DATA: String = "COIN_DATA"

        fun createIntent(context: Context, crypto: Crypto): Intent {
            val intent = Intent(context, CoinDetailedActivity::class.java)
            intent.putExtra(COIN_DATA, crypto)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detailed)
    }


}