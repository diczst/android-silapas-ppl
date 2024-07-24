package com.example.ppllapasfix.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityRegisterBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.login.LoginActivity
import com.github.drjacky.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import com.techiness.progressdialoglibrary.ProgressDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private val viewModel: RegisterViewModel by inject()
    private var fileImage: File? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        val items = listOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.selectionJenkel.setAdapter(adapter)

        binding.selectionJenkel.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                binding.selectionJenkel.error = null
            }

        binding.btnRegister.setOnClickListener{
            register()
        }

        binding.imgKtp.setOnClickListener {
            pickImage()
        }
    }

    private fun register(){
        if (binding.etNik.text!!.isEmpty()){
            binding.etNik.error = "NIK tidak boleh kosong"
            binding.etNik.requestFocus()
            return
        }

        if (binding.etNama.text!!.isEmpty()){
            binding.etNama.error = "NIK tidak boleh kosong"
            binding.etNama.requestFocus()
            return
        }

        if (binding.etHandphone.text!!.isEmpty()){
            binding.etHandphone.error = "Nomor HP tidak boleh kosong"
            binding.etHandphone.requestFocus()
            return
        }

        if (binding.etAlamat.text!!.isEmpty()){
            binding.etAlamat.error = "Alamat tidak boleh kosong"
            binding.etAlamat.requestFocus()
            return
        }

        if (binding.etPassword.text!!.isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            binding.etPassword.requestFocus()
            return
        }

        if (binding.selectionJenkel.text!!.isEmpty()) {
            binding.selectionJenkel.error = "Jenis Kelamin tidak boleh kosong"
            binding.selectionJenkel.requestFocus()
            return
        }

        if(fileImage == null){
            Toast.makeText(this, "Foto KTP tidak boleh kosong", Toast.LENGTH_LONG).show()
            return
        }

        val nik = binding.etNik.text.toString().toRequestBody("text/plain".toMediaType())
        val nama = binding.etNama.text.toString().toRequestBody("text/plain".toMediaType())
        val hp = binding.etHandphone.text.toString().toRequestBody("text/plain".toMediaType())
        val alamat = binding.etAlamat.text.toString().toRequestBody("text/plain".toMediaType())
        val password = binding.etPassword.text.toString().toRequestBody("text/plain".toMediaType())

        // jenkelnya boolean 0 or 1
        val selectionJenkel = binding.selectionJenkel.text.toString()
        var jenkel = 0.toString().toRequestBody("text/plain".toMediaType())
        if (selectionJenkel == "Perempuan"){
            jenkel = 1.toString().toRequestBody("text/plain".toMediaType())
        }
//        val selectionJenkel = binding.selectionJenkel.text.toString().toRequestBody("text/plain".toMediaType())

        if(fileImage != null){
//            val requestImageFile = fileImage?.asRequestBody("image/*".toMediaTypeOrNull())
//            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                "photo",
//                "jpg",
//                requestImageFile!!
//            )
            val file = fileImage.toMultipartBody()

            viewModel.createPengunjung(nik,nama,hp,jenkel,alamat,password, file!!).observe(this) {
                when(it.state){
                    State.SUCCESS -> {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Berhasil Mendaftar Akun, Selamat datang : ${it.data?.data?.nama}", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
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

    private fun pickImage() {
        ImagePicker.with(this)
            .crop()
            .maxResultSize(1080, 1080, true)
            .createIntentFromDialog { launcher.launch(it) }
    }

    private fun File?.toMultipartBody(name: String = "image"): MultipartBody.Part? {
        if (this == null) return null
        val reqFile: RequestBody = this.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, this.name, reqFile)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it.data?.data!!
            fileImage = File(uri.path ?: "")
            Picasso.get().load(uri).into(binding.imgKtp)
        }
    }
}