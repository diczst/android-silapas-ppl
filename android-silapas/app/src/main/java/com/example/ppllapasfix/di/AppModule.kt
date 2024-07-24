package com.example.ppllapasfix.di

import com.example.ppllapasfix.data.network.ApiConfig
import org.koin.dsl.module

val appModule = module {
    single { ApiConfig.provideApiService }
}