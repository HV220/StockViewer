package com.example.stockviewer.api.cryptocompare

enum class ApiCommonInfo(val urlMainSite: String, val urlApiSite: String) {
    ADDRESSES_SITE_CRYPTO(
        "https://www.cryptocompare.com/",
        "https://min-api.cryptocompare.com/"
    ),
}