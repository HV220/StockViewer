package com.example.stockviewer.api

import com.example.stockviewer.api.configurator.RetrofitConfigurator
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(
            url: String,
            configurator: RetrofitConfigurator,
            gsonDeserializer: Gson
        ): Retrofit {
            return retrofit ?: configurator.configure(
                Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create(gsonDeserializer))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            )
                .also { retrofit = it }
        }

        inline fun <reified T> getApiService(
            url: String,
            configurator: RetrofitConfigurator,
            gsonDeserializer: Gson
        ): T {
            return getRetrofitInstance(url, configurator, gsonDeserializer).create(T::class.java)
        }
    }
}