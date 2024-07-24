package com.example.ppllapasfix.ui.formulirtitipan

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityTitipanBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.antrian.AntrianActivity
import com.example.ppllapasfix.ui.login.LoginActivity
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.dayToBahasaIndonesia
import com.techiness.progressdialoglibrary.ProgressDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class TitipanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTitipanBinding

    companion object{
        const val WARGA_BINAAN_ID = "WARGA_BINAAN_ID"
        const val NAME: String = "NAME"
        const val TYPE: String = "TYPE"
    }

    private val viewModel: TitipanViewModel by inject()

    private lateinit var progressDialog: ProgressDialog

    // didapatkan dari pemilihan sebelumnya
    private var wargaBinaanId: Int = 0
    private var name: String? = ""
    private var dayName: String? = ""

    private var finalDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTitipanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        wargaBinaanId = intent.getIntExtra(WARGA_BINAAN_ID,0)
        name = intent.getStringExtra(NAME)

        binding.namaWbp.setText(name)
        val hubungan = listOf("Ayah Kandung", "Ibu Kandung", "Istri", "Anak", "Paman", "Bibi", "Kakek", "Nenek", "Lainnya")
        val adaptor = ArrayAdapter(this, R.layout.list_item, hubungan)
        binding.actvHubungan.setAdapter(adaptor)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val formatedDate = Date(selectedYear, selectedMonth, selectedDay - 1)
            dayName = sdf.format(formatedDate).dayToBahasaIndonesia()

            val sdfFull = SimpleDateFormat("dd MMMM", Locale.US);
            binding.actvTanggal.setText("$dayName, ${sdfFull.format(Date(selectedYear,selectedMonth,selectedDay))} $selectedYear")

            finalDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"

        }, year, month, day)

        binding.actvTanggal.setOnClickListener {
            val now = System.currentTimeMillis() - 1000
            dpd.datePicker.minDate = now
            dpd.datePicker.dayOfMonth.toDrawable()
            dpd.show()
        }

        binding.tvBackDisini.setOnClickListener{
            finish()
        }
        binding.tvProsesAntrian.setOnClickListener {
            prosesAntrian()
        }
    }

    private fun prosesAntrian(){

        if (binding.etKasus.text!!.isEmpty()){
            binding.etKasus.setError("Input Perkara Kasus")
            binding.etKasus.requestFocus()
            return
        } else {
            binding.etKasus.error = null
        }

        if (binding.actvHubungan.text!!.isEmpty()){
            binding.actvHubungan.setError("Pilih hubungan keluarga")
            binding.actvHubungan.requestFocus()
            return
        } else {
            binding.actvHubungan.error = null
        }

        if (binding.etTitipanuang.text!!.isEmpty()  && binding.etTitipanbarang.text!!.isEmpty()) {
            if (binding.etTitipanuang.text!!.isEmpty()){
                binding.etTitipanuang.setError("Titipan uang tidak boleh kosong")
                binding.etTitipanuang.requestFocus()
            } else {
                binding.etTitipanuang.error = null
            }

            if (binding.etTitipanbarang.text!!.isEmpty()){
                binding.etTitipanbarang.setError("Titipan barang tidak boleh kosong")
                binding.etTitipanbarang.requestFocus()
            } else {
                binding.etTitipanbarang.error = null
            }
            return
        }
        else {
            if (binding.etTitipanuang.text!!.isEmpty()){
                binding.etTitipanuang.error = null
            }

            if (binding.etTitipanbarang.text!!.isEmpty()){
                binding.etTitipanbarang.error = null
            }
        }



        if (binding.etNokamar.text!!.isEmpty()){
            binding.etNokamar.error = "Input No Kamar tidak boleh kosong"
            binding.etNokamar.requestFocus()
            return
        } else {
            binding.etNokamar.error = null

        }
        if (binding.actvTanggal.text!!.isEmpty()){
            binding.actvTanggal.error = "Tanggal tidak boleh kosong"
            binding.actvTanggal.requestFocus()
            return
        } else {
            binding.actvTanggal.error = null
        }

        val idWargaBinaan = wargaBinaanId.toString().toRequestBody("text/plain".toMediaType())
        val pengunjungId = MyPreference.getUser()?.id.toString().toRequestBody("text/plain".toMediaType())
        val kasus = binding.etKasus.text.toString().toRequestBody("text/plain".toMediaType())
        val hubKeluarga = binding.actvHubungan.text.toString().toRequestBody("text/plain".toMediaType())
        val titipanuang = binding.etTitipanuang.text.toString().toRequestBody("text/plain".toMediaType())
        val titipanbarang = binding.etTitipanbarang.text.toString().toRequestBody("text/plain".toMediaType())
        val nokamar = binding.etNokamar.text.toString().toRequestBody("text/plain".toMediaType())
        val convertedFinalDate = finalDate?.toRequestBody("text/plain".toMediaType())

        if(dayName == "Jum'at" || dayName == "Minggu"){
            MaterialDialog(this).show {
                title(R.string.terjadi_kesalahan)
                message(text = "Maaf titipan barang tidak bisa dilakukan pada hari jum'at dan minggu serta hari libur lainnya")
                negativeButton(R.string.ok) {
                }
            }
        } else {
            createTitipan(idWargaBinaan, pengunjungId, kasus, hubKeluarga, titipanbarang,  titipanuang, nokamar, convertedFinalDate!!)
        }

    }

    private fun createTitipan(binaan_id: RequestBody,
                              pengunjung_id: RequestBody,
                              kasus: RequestBody,
                              hub_keluarga: RequestBody,
                              barang: RequestBody,
                              uang: RequestBody,
                              nokamar: RequestBody,
                              tanggal: RequestBody,){
        viewModel.createTitipan(binaan_id, pengunjung_id, kasus, hub_keluarga, barang, uang, nokamar, tanggal).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    val intent = Intent(this, AntrianActivity::class.java)
                    intent.putExtra(AntrianActivity.STATE,"titipan")
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Berhasil Membuat Antrian", Toast.LENGTH_SHORT).show()
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    MaterialDialog(this).show {
                        title(R.string.antrian_kunjugan_tidak_ditemukan)
                        icon(R.drawable.ic_no_antrian)
                        message(text = it.message ?: "Terjadi kesalahan")
                        negativeButton(R.string.kembali) {
                            finish()
                        }
                    }
//                    Toast.makeText(this, it.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
                    progressDialog.show();
                }
            }

        }
    }
}