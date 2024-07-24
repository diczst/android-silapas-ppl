package com.example.ppllapasfix.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ppllapasfix.data.DataRepository
import com.example.ppllapasfix.data.request.LoginRequest
import com.example.ppllapasfix.databinding.ActivityLoginBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.home.HomeActivity
import com.example.ppllapasfix.ui.register.RegisterActivity
import com.techiness.progressdialoglibrary.ProgressDialog
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by inject()
    private lateinit var binding: ActivityLoginBinding
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        binding.tvKlikDisini.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.tvLoginDisini.setOnClickListener {
            login()
        }
    }

    private fun login(){
        // validation
        if (binding.etNik.text!!.isEmpty()){
            binding.etNik.error = "NIK tidak boleh kosong"
            return
        }
        if (binding.etPassword.text!!.isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            return
        }

        val body = LoginRequest(
            binding.etNik.text.toString(),
            binding.etPassword.text.toString()
        )

        viewModel.login(body).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Selamat datang : ${it.data?.nama}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, it.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
                    progressDialog.show();
                }
            }

        }
    }


}