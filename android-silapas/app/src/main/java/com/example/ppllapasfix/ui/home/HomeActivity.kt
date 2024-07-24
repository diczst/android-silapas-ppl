package com.example.ppllapasfix.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems
import com.example.ppllapasfix.*
import com.example.ppllapasfix.databinding.ActivityHomeBinding
import com.example.ppllapasfix.ui.antrian.AntrianActivity
import com.example.ppllapasfix.ui.informasiakun.InformasiAkunActivity
import com.example.ppllapasfix.ui.login.LoginActivity
import com.example.ppllapasfix.ui.pemilihanwargabinaan.PemilihanWargaBinaanActivity
import com.example.ppllapasfix.utils.MyPreference

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(MyPreference.isLogin){
            binding.tvRegLog.text = "Informasi Akun"
            binding.imgRegLog.setImageResource(R.drawable.ic_baseline_verified_user_24)
            binding.tvAccount.setOnClickListener {
                val intent = Intent(this, InformasiAkunActivity::class.java)
                startActivity(intent)
            }

            binding.tvPengajuan.setOnClickListener {
                val intent = Intent(this, PemilihanWargaBinaanActivity::class.java)
                intent.putExtra(PemilihanWargaBinaanActivity.STATE,"pengajuan")
                startActivity(intent)
            }
        } else {
            binding.tvPengajuan.visibility = View.GONE
            binding.tvAccount.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.tvJadwalDisini.setOnClickListener {
            val intent = Intent(this, JadwalActivity::class.java)
            startActivity(intent)
        }

        binding.tvTitipanDisini.setOnClickListener {
            if(MyPreference.isLogin){
                val intent = Intent(this, PemilihanWargaBinaanActivity::class.java)
                intent.putExtra(PemilihanWargaBinaanActivity.STATE,"titipan")
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.tvKunjunganDisini.setOnClickListener {
            if(MyPreference.isLogin){
                val intent = Intent(this, PemilihanWargaBinaanActivity::class.java)
                intent.putExtra(PemilihanWargaBinaanActivity.STATE,"kunjungan")
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.integrasi.setOnClickListener {

            if(MyPreference.isLogin){
                val intent = Intent(this, PemilihanWargaBinaanActivity::class.java)
                intent.putExtra(PemilihanWargaBinaanActivity.STATE,"integrasi")
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }

        binding.layoutAntrian.setOnClickListener {
            val myItems = listOf("Antrian Kunjungan", "Antrian Titipan Barang")

            if(MyPreference.isLogin){
                MaterialDialog(this).show {
                    title(R.string.pilih_antrian)
                    icon(R.drawable.ic_no_antrian)
                    message(R.string.pesan_pilih)
                    negativeButton(R.string.batal)
                    listItems(items = myItems) { _, index, _ ->
                        when(index){
                            0 -> {
                                dismiss() // tutup dialog saat pindah activity
                                val intent = Intent(this@HomeActivity, AntrianActivity::class.java)
                                intent.putExtra(AntrianActivity.STATE,"kunjungan")
                                startActivity(intent)
                            }
                            1 ->{
                                dismiss() // tutup dialog saat pindah activity
                                val intent = Intent(this@HomeActivity, AntrianActivity::class.java)
                                intent.putExtra(AntrianActivity.STATE,"titipan")
                                startActivity(intent)
                            }
                        }
                    }
                }
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }




        }
    }
}