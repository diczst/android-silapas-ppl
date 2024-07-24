package com.example.ppllapasfix.ui.pemilihanwargabinaan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.example.ppllapasfix.R
import com.example.ppllapasfix.adapter.PemilihanWargaBinaanAdapter
import com.example.ppllapasfix.databinding.ActivityPemilihanWargaBinaanBinding
import com.example.ppllapasfix.network.State
import com.techiness.progressdialoglibrary.ProgressDialog
import org.koin.android.ext.android.inject

class PemilihanWargaBinaanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemilihanWargaBinaanBinding
    private val viewModel: PemilihanWargaBinaanViewModel by inject()
    private lateinit var progressDialog: ProgressDialog
    private val adapter = PemilihanWargaBinaanAdapter()

    var state = ""

    companion object {
        const val STATE = "STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemilihanWargaBinaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state = intent.getStringExtra(STATE).toString()

        progressDialog = ProgressDialog(this)

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnSearch.setOnClickListener {
            searchData(binding.edtTextName.text.toString())
        }

        getData()
        adapter.state = state
        binding.rvWords.adapter = adapter
    }

    // metode data dengan ambil data yang dikembalikan oleh api saja berdasarkan argumen
    private fun searchData(search: String){
        viewModel.search(search).observe(this) {
            when (it.state) {
                State.SUCCESS -> {
//                    binding.tvError.toGone()
                    progressDialog.dismiss()
                    val data = it.data ?: emptyList()
                    adapter.addItems(data)

                }
                State.ERROR -> {
                    progressDialog.dismiss()
//                    binding.tvError.toVisible()
//                    Log.i("StoreAddressActivity", "getData: ${it.message}")

                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }

    private fun getData() {
        viewModel.get().observe(this) {
            when (it.state) {
                State.SUCCESS -> {
//                    binding.tvError.toGone()
                    progressDialog.dismiss()
                    val data = it.data ?: emptyList()

                    if (state == "pengajuan"){
                        val filtered = data.filter { wbp -> wbp.kategori != "Tahanan" }
                        adapter.addItems(filtered)
                    } else {
                        adapter.addItems(data)
                    }
//                    if (data.isEmpty()) {
//                        binding.tvError.toVisible()
//                    }
                }
                State.ERROR -> {
                    progressDialog.dismiss()
//                    binding.tvError.toVisible()
//                    Log.i("StoreAddressActivity", "getData: ${it.message}")

                }
                State.LOADING -> {
                    progressDialog.show()
                }
            }
        }
    }
}