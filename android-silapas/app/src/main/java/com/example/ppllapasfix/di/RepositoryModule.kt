package com.example.ppllapasfix.di

import com.example.ppllapasfix.data.DataRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {DataRepository(get())}
}