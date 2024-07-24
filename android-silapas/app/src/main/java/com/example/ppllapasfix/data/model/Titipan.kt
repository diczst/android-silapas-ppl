package com.example.ppllapasfix.data.model

data class Titipan(
    val id: Int,
    val binaan_id: Int,
    val pengunjung_id: Int,
    val kasus: String,
    val hub_keluarga: String,
    val barang:String,
    val uang:Int,
    val nokamar:String,
    val tanggal:String,
    val no_antrian: Int,
    val status: String,

    // catatan:
    // status tidak bisa pake boolean, error : boolean cannot be number
)