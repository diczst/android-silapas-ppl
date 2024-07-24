package com.example.ppllapasfix.di

import android.app.Application
import com.chibatching.kotpref.Kotpref
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin{
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
    }
}