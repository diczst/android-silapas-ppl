package com.example.ppllapasfix.ui.informasiakun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityInformasiAkunBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.antrian.AntrianViewModel
import com.example.ppllapasfix.ui.home.HomeActivity
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.dayToBahasaIndonesia
import com.techiness.progressdialoglibrary.ProgressDialog
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class InformasiAkunActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformasiAkunBinding
    private val viewModel: InformasiAkunViewModel by inject()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(this)
        binding = ActivityInformasiAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvBackDisini.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            MaterialDialog(this).show {
                    title(R.string.keluar)
                    icon(R.drawable.ic_exit)
                    message(R.string.yakin_keluar)
                    negativeButton(R.string.batal)
                    positiveButton(R.string.ya) {
                        MyPreference.isLogin = false
                        val intent = Intent(this@InformasiAkunActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this@InformasiAkunActivity, "Berhasil keluar akun", Toast.LENGTH_LONG).show()
                    }
                }
        }

        getInformasiAkun()
    }

    fun getInformasiAkun(){
        viewModel.getInformasiAkun(MyPreference.getUser()?.id.toString()).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    val data = it.data
                    progressDialog.dismiss()
                    binding.tvName.text = data?.nama.toString()

                    if(data?.jenkel.toString() == "0"){
                        binding.tvJenkel.text = "Laki-laki"
                    } else {
                        binding.tvJenkel.text = "Perempuan"
                    }

                    binding.tvNik.text = data?.nik.toString()
                    binding.tvHp.text = data?.no_handphone.toString()
                    binding.tvAlamat.text = data?.alamat.toString()
                }

                State.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }
}