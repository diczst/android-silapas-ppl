package com.example.ppllapasfix.data.model

data class Kunjungan (
    val id: Int,
    val binaan_id: Int,
    val pengunjung_id: Int,
    val hub_keluarga: String,
    val tanggal: String,
    val no_antrian: Int,
    val status: String,
    val keterangan: String,
    val bukti_vaksin: String
)