package com.example.ppllapasfix.data.model

data class WargaBinaan (
    val id: Int,
    val pengunjung_id: Int?,
    val nama: String,
    val kategori: String,
    val lama_pidana: String,
    val jenis_kejahatan: String,
    val tanggal_mulai: String?,
    val tanggal_berakhir: String?,
    val tanggal_pengajuan: String?
)