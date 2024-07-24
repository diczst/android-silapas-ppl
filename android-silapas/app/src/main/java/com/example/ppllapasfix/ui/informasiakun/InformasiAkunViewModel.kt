package com.example.ppllapasfix.ui.informasiakun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository

class InformasiAkunViewModel(val repository: DataRepository): ViewModel() {
    fun getInformasiAkun(id:String?) = repository.getInformasiAkun(id).asLiveData()
}