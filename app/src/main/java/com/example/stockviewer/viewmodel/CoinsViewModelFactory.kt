package com.example.stockviewer.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CoinsViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoinsViewModel::class.java)) {
            return CoinsViewModel(application) as T
        }
        return super.create(modelClass)
    }
}