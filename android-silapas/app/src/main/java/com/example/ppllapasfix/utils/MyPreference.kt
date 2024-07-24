package com.example.ppllapasfix.utils

import com.chibatching.kotpref.KotprefModel
import com.example.ppllapasfix.data.model.Pengunjung

object MyPreference: KotprefModel() {
    var isLogin by booleanPref(false)
    var pengunjung by stringPref()

    fun setUser(user: Pengunjung?){
        this.pengunjung = user.toJson()
    }

    fun getUser(): Pengunjung? {
        if(pengunjung.isEmpty()) return null
        return pengunjung.toModel(Pengunjung::class.java)
    }
}

//fun getTokoId() = Prefs.getUser()?.toko?.id