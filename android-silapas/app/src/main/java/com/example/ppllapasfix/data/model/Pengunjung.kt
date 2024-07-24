package com.example.ppllapasfix.data.model

data class Pengunjung(
    val id: Int,
    val nik: String,
    val nama: String,
    val no_handphone: String,
    val jenkel: Int,
    val alamat: String,
    val image: String

    // catatan:
    // tidak bisa pake boolean error : boolean cannot be number
)
