package com.example.stockviewer.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stockviewer.R
import com.example.stockviewer.adapter.cryptocompare.CoinAdapter
import com.example.stockviewer.api.cryptocompare.responce.Crypto
import com.example.stockviewer.databinding.ActivityMainBinding
import com.example.stockviewer.viewmodel.CoinsViewModel
import com.example.stockviewer.viewmodel.CoinsViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen


class CoinsActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinsViewModel: CoinsViewModel
    private lateinit var adapter: CoinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        coinsViewModel =
            ViewModelProvider(
                this, CoinsViewModelFactory(application)
            )[CoinsViewModel::class.java]

        adapter = CoinAdapter()

        initOnClickViewModel()
        initOnClickAdapter()

        binding.coinsRecyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.coinsRecyclerView.adapter = adapter

        coinsViewModel.loadInfoAboutCoins()
    }

    private fun initOnClickViewModel() {
        coinsViewModel.let {
            it.getInfoAboutCoins().observe(this) { result ->
                adapter.dataCoins = result
            }
        }
        coinsViewModel.getInfoCoinsLoading().observe(this) { isLoading ->
            if (isLoading) {
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
            }
        }
        coinsViewModel.getError()
            .observe(this) { isError ->
                if (isError)
                    Toast.makeText(
                        this,
                        getString(
                            R.string.internal_error
                        ),
                        Toast.LENGTH_LONG
                    ).show()
            }

        coinsViewModel.getNetworkAvailable().observe(this) { isNetworkConnect ->
            if (!isNetworkConnect) {
                binding.networkConnection.visibility = View.VISIBLE
                binding.networkConnection.text = getString(
                    R.string.no_network_connect
                )
            } else {
                binding.networkConnection.visibility = View.GONE

            }
        }
    }

    private fun initOnClickAdapter() {
        adapter.onReachEndListener = object : CoinAdapter.OnReachEndListener {
            override fun doOnReachEnd() {
                coinsViewModel.loadInfoAboutCoins()
            }
        }
        adapter.onContextNoInformationListener =
            object : CoinAdapter.OnContextNoInformationListener {
                override fun setContextErrorForUser(): String {
                    return resources.getString(R.string.descriptionCoinNotExist)
                }
            }
        adapter.onSetClickCoinListener = object : CoinAdapter.OnSetClickCoinListener {
            override fun setClickCoin(coin: Crypto) {
                val intent =
                    CoinDetailedActivity.createIntent(this@CoinsActivity, coin)
                startActivity(intent)
            }
        }
    }
}
