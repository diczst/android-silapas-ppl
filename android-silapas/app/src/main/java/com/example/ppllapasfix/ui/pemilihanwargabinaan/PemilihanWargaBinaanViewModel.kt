package com.example.ppllapasfix.ui.pemilihanwargabinaan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository

class PemilihanWargaBinaanViewModel(val repository: DataRepository): ViewModel() {
    fun get() = repository.getWargaBinaan().asLiveData()
    fun search(search:String?) = repository.searchWargaBinaan(search).asLiveData()
}