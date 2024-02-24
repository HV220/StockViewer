package com.example.stockviewer.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.stockviewer.adapter.cryptocompare.CoinAdapter
import com.example.stockviewer.databinding.ActivityMainBinding
import com.example.stockviewer.viewmodel.CoinsViewModel
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

        coinsViewModel = ViewModelProvider(this)[CoinsViewModel::class.java]
        adapter = CoinAdapter()

        initOnClickViewModel()
        initOnClickAdapter()

        binding.coinsRecyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.coinsRecyclerView.adapter = adapter

        coinsViewModel.loadInfoAboutCoins()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initOnClickViewModel() {
        coinsViewModel.let {
            it.getInfoAboutCoins().observe(this) { result ->
                adapter.dataCoins = result
            }
        }
    }

    private fun initOnClickAdapter() {
        adapter.onReachEndListener = object : CoinAdapter.OnReachEndListener {
            override fun doOnReachEnd() {
                coinsViewModel.loadInfoAboutCoins()
            }
        }
    }
}
