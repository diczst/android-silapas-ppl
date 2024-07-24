package com.example.ppllapasfix.di

import com.example.ppllapasfix.ui.antrian.AntrianViewModel
import com.example.ppllapasfix.ui.formulirkunjungan.KunjunganViewmodel
import com.example.ppllapasfix.ui.formulirtitipan.TitipanViewModel
import com.example.ppllapasfix.ui.informasiakun.InformasiAkunViewModel
import com.example.ppllapasfix.ui.login.LoginViewModel
import com.example.ppllapasfix.ui.pemilihanwargabinaan.PemilihanWargaBinaanViewModel
import com.example.ppllapasfix.ui.pengajuan.PengajuanViewModel
import com.example.ppllapasfix.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PemilihanWargaBinaanViewModel(get()) }
    viewModel { KunjunganViewmodel(get()) }
    viewModel { AntrianViewModel(get()) }
    viewModel { TitipanViewModel(get()) }
    viewModel { InformasiAkunViewModel(get()) }
    viewModel { PengajuanViewModel(get()) }
}