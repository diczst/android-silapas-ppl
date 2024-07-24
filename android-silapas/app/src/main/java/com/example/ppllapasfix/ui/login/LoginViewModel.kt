package com.example.ppllapasfix.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ppllapasfix.data.DataRepository
import com.example.ppllapasfix.data.request.LoginRequest

class LoginViewModel(private val dataRepository: DataRepository): ViewModel() {
    fun login(data: LoginRequest) = dataRepository.login(data).asLiveData()
}