package com.example.stockviewer.api.configurator

import retrofit2.Retrofit

class RetrofitDefaultConfigurator : RetrofitConfigurator {
    override fun configure(builder: Retrofit.Builder): Retrofit {
        return builder.build()
    }
}