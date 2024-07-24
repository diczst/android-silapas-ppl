package com.example.ppllapasfix.ui.antrian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ppllapasfix.R
import com.example.ppllapasfix.data.request.BatalAntrianRequest
import com.example.ppllapasfix.databinding.ActivityAntrianBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.dayToBahasaIndonesia
import com.techiness.progressdialoglibrary.ProgressDialog
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class AntrianActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAntrianBinding
    private val viewModel: AntrianViewModel by inject()
    private lateinit var progressDialog: ProgressDialog
    private val pengunjungId = MyPreference.getUser()?.id.toString()

    companion object {
        const val STATE = "STATE"
    }

    var state = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAntrianBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state = intent.getStringExtra(STATE).toString()

        progressDialog = ProgressDialog(this)

        binding.tvBackDisini.setOnClickListener {
            finish()
        }

        if(state == "kunjungan"){
            binding.tvTitle.text = "Kunjungan WBP"
            kunjungan()
        } else {
            binding.tvTitle.text = "Titipan Barang"
            titipan()
        }
    }

    fun kunjungan(){
        viewModel.getKunjungan(pengunjungId).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    val data = it.data
                    val responseDate = data?.tanggal.toString()

                    val year = responseDate.subSequence(0,4).toString().toInt()  // 0,1,2,3
                    val month = responseDate.subSequence(5,7).toString().toInt() // 5,6
                    val date = responseDate.subSequence(8,10) .toString().toInt()// 8,9
                    val sdfFull = SimpleDateFormat("dd MMMM", Locale.US)

                    // Jangan lupa tambahkan Locale.us
                    // tanpa ini, di beberapa hp tanggal akan menjadi date unknown

                    val sdf = SimpleDateFormat("EEEE", Locale.US)
                    val dayDate = Date(year, month, date - 4)
                    val dayName = sdf.format(dayDate).dayToBahasaIndonesia()

                    val formatedDate = "$dayName, ${sdfFull.format(Date(year, month - 1, date))} $year"

                    binding.tvNoAntrian.text = data?.no_antrian.toString()
                    binding.tvTanggal.text = formatedDate

                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    MaterialDialog(this).show {
                        title(R.string.antrian_kunjugan_tidak_ditemukan)
                        icon(R.drawable.ic_no_antrian)
                        message(R.string.notifikasi_antrian_kunjugan)
                        cancelOnTouchOutside(false)
                        negativeButton(R.string.kembali) {
                            finish()
                        }
                    }

                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }

        }
        binding.tvBatalkanAntrian.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.batalkan_antrian_kunjungan)
                icon(R.drawable.ic_no_antrian)
                message(R.string.membatalkan_antrian_kunjungan)
                negativeButton(R.string.tidak)
                positiveButton(R.string.ya) {
                    val request = BatalAntrianRequest()
                    viewModel.batalkanKunjungan(pengunjungId,request).observe(this@AntrianActivity){
                        when(it.state){
                            State.SUCCESS -> {
                                progressDialog.dismiss()
                                Toast.makeText(this@AntrianActivity, "Kunjungan anda berhasil dibatalkan", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            State.ERROR -> {
                                progressDialog.dismiss()
                                Toast.makeText(this@AntrianActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                            State.LOADING -> {
                                progressDialog.show()
                            }
                        }
                    }
                }
            }
        }
    }

    fun titipan(){
        viewModel.getTitipan(pengunjungId).observe(this){
            when (it.state) {
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    val data = it.data
                    val responseDate = data?.tanggal.toString()

                    val year = responseDate.subSequence(0,4).toString().toInt()  // 0,1,2,3
                    val month = responseDate.subSequence(5,7).toString().toInt() // 5,6
                    val date = responseDate.subSequence(8,10) .toString().toInt()// 8,9
                    val sdfFull = SimpleDateFormat("dd MMMM", Locale.US);

                    // Jangan lupa tambahkan Locale.us
                    // tanpa ini, di beberapa hp tanggal akan menjadi date unknown

                    val sdf = SimpleDateFormat("EEEE", Locale.US)
                    val dayDate = Date(year, month, date - 3)
                    val dayName = sdf.format(dayDate).dayToBahasaIndonesia()
                    val formatedDate = "$dayName, ${sdfFull.format(Date(year, month - 1, date))} $year"
                    binding.tvNoAntrian.text = data?.no_antrian.toString()
                    binding.tvTanggal.text = formatedDate

                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    MaterialDialog(this).show {
                        title(R.string.antrian_kunjugan_tidak_ditemukan)
                        icon(R.drawable.ic_no_antrian)
                        message(R.string.notifikasi_antrian_titipan)
                        cancelOnTouchOutside(false)
                        negativeButton(R.string.kembali) {
                            finish()
                        }
                    }

                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
        binding.tvBatalkanAntrian.setOnClickListener {
            MaterialDialog(this).show {
                title(R.string.batalkan_antrian_kunjungan)
                icon(R.drawable.ic_no_antrian)
                message(R.string.membatalkan_antrian_kunjungan)
                negativeButton(R.string.tidak)
                positiveButton(R.string.ya) {
                    val request = BatalAntrianRequest()
                    viewModel.batalkanTitipan(pengunjungId,request).observe(this@AntrianActivity){
                        when(it.state){
                            State.SUCCESS -> {
                                progressDialog.dismiss()
                                Toast.makeText(this@AntrianActivity, "Titipan anda berhasil dibatalkan", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            State.ERROR -> {
                                progressDialog.dismiss()
                                Toast.makeText(this@AntrianActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                            State.LOADING -> {
                                progressDialog.show()
                            }
                        }
                    }
                }
            }
        }
    }
}