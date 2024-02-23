package com.example.stockviewer.view

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stockviewer.databinding.ActivityMainBinding
import com.example.stockviewer.viewmodel.CoinsViewModel
import com.jakewharton.threetenabp.AndroidThreeTen

class CoinsActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var coinsViewModel: CoinsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)
        coinsViewModel = ViewModelProvider(this)[CoinsViewModel::class.java]
        initOnClickViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initOnClickViewModel() {
        coinsViewModel.let { it ->
            it.getInfoAboutCoins().observe(this) { result ->
                Log.d("MainActivity", result.toString())
            }
        }
    }
}
