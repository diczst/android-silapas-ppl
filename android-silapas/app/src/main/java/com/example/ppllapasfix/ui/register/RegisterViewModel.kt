package com.example.ppllapasfix.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun createPengunjung(nik: RequestBody,
              nama: RequestBody,
              no_handphone: RequestBody,
              jenkel: RequestBody,
              alamat: RequestBody,
              password: RequestBody,
              file: MultipartBody.Part) =

        dataRepository.createPengunjung(
            nik, nama, no_handphone, jenkel, alamat, password, file
        ).asLiveData()
}