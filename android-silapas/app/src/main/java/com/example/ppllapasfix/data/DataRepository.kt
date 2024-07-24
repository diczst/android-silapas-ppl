package com.example.ppllapasfix.data

import android.util.Log
import com.example.ppllapasfix.data.network.ApiService
import com.example.ppllapasfix.data.request.BatalAntrianRequest
import com.example.ppllapasfix.data.request.LoginRequest
import com.example.ppllapasfix.network.Resource
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.getErrorBody
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part

class DataRepository(private val apiService: ApiService) {

    fun login(data: LoginRequest) = flow{
        emit(Resource.loading(null))
        try {
            apiService.login(data).let {
                if (it.isSuccessful) {
                    MyPreference.isLogin = true
                    val body = it.body()
                    val user = body?.data

                    MyPreference.setUser(user)
                    emit(Resource.success(user))
                } else {
                    emit(Resource.error( it.getErrorBody()?.message ?: "Error Default", null))
                    Log.e("Login Error : ", it.message())
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
            Log.e("Login Error : ", e.message ?: "Terjadi Kesalahan")
        }
    }

    fun createPengunjung(
        nik: RequestBody,
        nama: RequestBody,
        no_handphone: RequestBody,
        jenkel: RequestBody,
        alamat: RequestBody,
        password: RequestBody,
        file: MultipartBody.Part

    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.createPengunjung(nik, nama, no_handphone, jenkel, alamat, password, file).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
                    Log.i("AppRepository", "success: ${body?.data}")
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                    Log.i("AppRepository", "failed: ${it.getErrorBody()?.message}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun getInformasiAkun(pengunjungId: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getInformasiAkun(pengunjungId).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data
                    if(data != null){
                        emit(Resource.success(data))
                    } else {
                        emit(Resource.error("response berhasil tapi data null",null))
                    }

                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
            Log.e("Login Error : ", e.message ?: "Terjadi Kesalahan")
        }
    }

    fun getWargaBinaan() = flow {
        emit(Resource.loading(null))
        try {
            apiService.getWargaBinaan().let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
//                    Log.i("TAG", "getUser: {${it.body()}}")
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
//                    Log.i("TAG", "getUser: {${it.getErrorBody()?.message}}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun searchWargaBinaan(search: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.searchWargaBinaan(search).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data
                    emit(Resource.success(data))
                    //Log.i("TAG", "getUser: {${it.body()}}")

                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                    //Log.i("TAG", "getUser: {${it.getErrorBody()?.message}}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun createKunjungan(
        binaan_id: RequestBody,
        pengunjung_id: RequestBody,
        hub_keluarga: RequestBody,
        tanggal: RequestBody,
        keterangan: RequestBody,
        file: MultipartBody.Part
    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.createKunjungan(binaan_id, pengunjung_id, hub_keluarga, tanggal, keterangan, file).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
                    Log.i("AppRepository", "success: ${body?.data}")
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                    Log.i("AppRepository", "failed: ${it.getErrorBody()?.message}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun getKunjungan(pengunjungId: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getKunjungan(pengunjungId).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data

                    if(data != null){
                        emit(Resource.success(data))
                    } else {
                        emit(Resource.error("response berhasil tapi data null",null))
                    }

                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
            Log.e("Login Error : ", e.message ?: "Terjadi Kesalahan")
        }
    }

    fun batalkanKunjungan(id:String, request: BatalAntrianRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.batalkanKunjungan(id, request).let {
                if (it.isSuccessful) {
                    val body = it.body()?.data
                    emit(Resource.success(body))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun createTitipan(
        binaan_id: RequestBody,
        pengunjung_id: RequestBody,
        kasus: RequestBody,
        hub_keluarga: RequestBody,
        barang: RequestBody,
        uang: RequestBody,
        nokamar: RequestBody,
        tanggal: RequestBody,
    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.createTitipan(binaan_id, pengunjung_id, kasus, hub_keluarga, barang, uang, nokamar, tanggal).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
 //                   Log.i("AppRepository", "success: ${body?.data}")
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
 //                   Log.i("AppRepository", "failed: ${it.getErrorBody()?.message}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun getTitipan(pengunjungId: String?) = flow {
        emit(Resource.loading(null))
        try {
            apiService.getTitipan(pengunjungId).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    val data = body?.data

                    if(data != null){
                        emit(Resource.success(data))
                    } else {
                        emit(Resource.error("response berhasil tapi data null",null))
                    }

                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
            Log.e("Login Error : ", e.message ?: "Terjadi Kesalahan")
        }
    }

    fun batalTitipan(id:String, request: BatalAntrianRequest) = flow {
        emit(Resource.loading(null))
        try {
            apiService.batalkanTitipan(id, request).let {
                if (it.isSuccessful) {
                    val body = it.body()?.data
                    emit(Resource.success(body))
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }

    fun createPengajuan(
        pengunjung_id: RequestBody,
        file: MultipartBody.Part
    ) = flow {
        emit(Resource.loading(null))
        try {
            apiService.createPengajuan(pengunjung_id, file).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
                    Log.i("AppRepository", "success: ${body?.data}")
                } else {
                    emit(Resource.error(it.getErrorBody()?.message ?: "Default error dongs", null))
                    Log.i("AppRepository", "failed: ${it.getErrorBody()?.message}")
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message ?: "Terjadi Kesalahan", null))
        }
    }
}