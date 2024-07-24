package com.example.ppllapasfix.ui.formulirtitipan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import okhttp3.RequestBody

class TitipanViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun createTitipan (binaan_id: RequestBody,
                       pengunjung_id: RequestBody,
                       kasus: RequestBody,
                       hub_keluarga: RequestBody,
                       barang: RequestBody,
                       uang: RequestBody,
                       nokamar: RequestBody,
                       tanggal: RequestBody,) =

        dataRepository.createTitipan(
            binaan_id, pengunjung_id, kasus, hub_keluarga, barang, uang, nokamar, tanggal
        ).asLiveData()
}