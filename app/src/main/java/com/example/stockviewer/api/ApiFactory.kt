package com.example.stockviewer.api

import com.example.stockviewer.api.configurator.RetrofitConfigurator
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(url: String, configurator: RetrofitConfigurator): Retrofit {

            return retrofit ?: configurator.configure(
                Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            )
                .also { retrofit = it }
        }

        inline fun <reified T> getApiService(url: String, configurator: RetrofitConfigurator): T {
            return getRetrofitInstance(url, configurator).create(T::class.java)
        }
    }
}