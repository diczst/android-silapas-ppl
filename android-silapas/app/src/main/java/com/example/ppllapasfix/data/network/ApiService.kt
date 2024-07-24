package com.example.ppllapasfix.data.network

import com.example.ppllapasfix.data.model.Kunjungan
import com.example.ppllapasfix.data.model.Pengunjung
import com.example.ppllapasfix.data.model.Titipan
import com.example.ppllapasfix.data.model.WargaBinaan
import com.example.ppllapasfix.data.request.BatalAntrianRequest
import com.example.ppllapasfix.data.request.LoginRequest
import com.example.ppllapasfix.data.response.BaseListResponse
import com.example.ppllapasfix.data.response.BaseSingleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("loginpengunjung")
    suspend fun login(
      @Body data: LoginRequest
    ): Response<BaseSingleResponse<Pengunjung>>

    @Multipart
    @POST("pengunjung")
    suspend fun createPengunjung(
        @Part("nik") nik: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("no_handphone") no_handphone: RequestBody,
        @Part("jenkel") jenkel: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("password") password: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<BaseSingleResponse<Pengunjung>>

    @GET("pengunjung/{pengunjung_id}")
    suspend fun getInformasiAkun(
        @Path("pengunjung_id") pengunjung_id: String? = null
    ): Response<BaseSingleResponse<Pengunjung>>

    @GET("wargabinaan")
    suspend fun getWargaBinaan(
    ): Response<BaseListResponse<WargaBinaan>>

    @GET("wargabinaan/search/{nama}")
    suspend fun searchWargaBinaan(
        @Path("nama") nama: String? = null
    ): Response<BaseListResponse<WargaBinaan>>

    @GET("kunjungan/{pengunjung_id}")
    suspend fun getKunjungan(
        @Path("pengunjung_id") pengunjung_id: String? = null
    ): Response<BaseSingleResponse<Kunjungan>>

    @Multipart
    @POST("kunjungan")
    suspend fun createKunjungan(
        @Part("binaan_id") binaan_id: RequestBody,
        @Part("pengunjung_id") pengunjung_id: RequestBody,
        @Part("hub_keluarga") hub_keluarga: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("keterangan") keterangan: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<BaseSingleResponse<Kunjungan>>

    @PUT("batalkankunjungan/{id}")
    suspend fun batalkanKunjungan(
        @Path("id") id: String,
        @Body data: BatalAntrianRequest
    ): Response<BaseSingleResponse<Kunjungan>>

    @Multipart
    @POST("titipanbarang")
    suspend fun createTitipan(
        @Part("binaan_id") binaan_id: RequestBody,
        @Part("pengunjung_id") pengunjung_id: RequestBody,
        @Part("kasus") kasus: RequestBody,
        @Part("hub_keluarga") hub_keluarga: RequestBody,
        @Part("barang") barang: RequestBody,
        @Part("uang") uang: RequestBody,
        @Part("nokamar") nokamar: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
    ): Response<BaseSingleResponse<Titipan>>

    @PUT("batalkantitipan/{id}")
    suspend fun batalkanTitipan(
        @Path("id") id: String,
        @Body data: BatalAntrianRequest
    ): Response<BaseSingleResponse<Titipan>>

    @GET("titipanbarang/{pengunjung_id}")
    suspend fun getTitipan(
        @Path("pengunjung_id") pengunjung_id: String? = null
    ): Response<BaseSingleResponse<Titipan>>

    @Multipart
    @POST("pengajuan")
    suspend fun createPengajuan(
        @Part("pengunjung_id") binaan_id: RequestBody,
        @Part file: MultipartBody.Part,
    ): Response<BaseSingleResponse<Kunjungan>>

}
