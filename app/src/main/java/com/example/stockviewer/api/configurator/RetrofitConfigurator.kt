package com.example.stockviewer.api.configurator

import retrofit2.Retrofit

interface RetrofitConfigurator {
    fun configure(builder: Retrofit.Builder): Retrofit
}