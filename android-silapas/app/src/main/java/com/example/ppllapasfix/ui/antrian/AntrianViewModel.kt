package com.example.ppllapasfix.ui.antrian

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import com.example.ppllapasfix.data.request.BatalAntrianRequest

class AntrianViewModel(val repository: DataRepository) :ViewModel() {
    fun getKunjungan(id:String?) = repository.getKunjungan(id).asLiveData()
    fun batalkanKunjungan(id: String, request: BatalAntrianRequest) = repository.batalkanKunjungan(id, request).asLiveData()

    fun getTitipan(id:String?) = repository.getTitipan(id).asLiveData()
    fun batalkanTitipan(id: String, request: BatalAntrianRequest) = repository.batalTitipan(id, request).asLiveData()
}