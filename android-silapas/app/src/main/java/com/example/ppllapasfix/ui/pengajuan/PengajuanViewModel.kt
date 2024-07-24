package com.example.ppllapasfix.ui.pengajuan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

class PengajuanViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun createPengajuan(pengunjung_id: RequestBody,
                        file: MultipartBody.Part) =
        dataRepository.createPengajuan(
            pengunjung_id, file
        ).asLiveData()
}

