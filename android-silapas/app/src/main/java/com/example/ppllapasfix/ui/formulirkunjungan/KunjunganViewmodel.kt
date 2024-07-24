package com.example.ppllapasfix.ui.formulirkunjungan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

class KunjunganViewmodel(private val dataRepository: DataRepository): ViewModel() {
    fun createKunjungan(binaan_id: RequestBody,
                         pengunjung_id: RequestBody,
                         hub_keluarga: RequestBody,
                         tanggal: RequestBody,
                         keterangan: RequestBody,
                         file: MultipartBody.Part) =

        dataRepository.createKunjungan(
            binaan_id, pengunjung_id, hub_keluarga, tanggal, keterangan, file
        ).asLiveData()
}

