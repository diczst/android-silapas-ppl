package com.example.ppllapasfix.ui.pengajuan

import android.R.attr.path
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityPengajuanBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.antrian.AntrianActivity
import com.example.ppllapasfix.ui.wargabinaandetail.WargaBinaanDetailActivity
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.getPath
import com.techiness.progressdialoglibrary.ProgressDialog
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File


class PengajuanActivity : AppCompatActivity() {

    private val viewModel: PengajuanViewModel by inject()

    companion object{
        const val WARGA_BINAAN_ID = "WARGA_BINAAN_ID"
        const val NAME: String = "NAME"
        const val STATUS = "STATUS"
    }

    private var pdfFile: File? = null
    private var name: String? = ""
    var status = false

    private lateinit var progressDialog: ProgressDialog

    private lateinit var binding: ActivityPengajuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengajuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        name = intent.getStringExtra(NAME)
        status = intent.getBooleanExtra(PengajuanActivity.STATUS, false)

        binding.textNamaWbp.text = "Nama WBP : $name"

        binding.img.setOnClickListener {
            selectPdf()
        }

        if(status) {
            binding.btnUnggah.setOnClickListener {
                prosesPengajuan()
            }
        } else {
            binding.btnUnggah.isEnabled = false
            binding.btnUnggah.setBackgroundColor(getColor(R.color.black))
            binding.btnUnggah.alpha = 0.2f
        }

    }

    private fun prosesPengajuan(){
        if(pdfFile == null){
            Toast.makeText(this, "Harap upload berkas terlebih dahulu", Toast.LENGTH_LONG).show()
            return
        }

        val file = pdfFile.toMultipartBody("berkas")
        val pengunjungId = MyPreference.getUser()?.id.toString().toRequestBody("text/plain".toMediaType())
        createKunjungan(pengunjungId, file!!);
    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // For loading PDF
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                var pdfUri = data?.data!!
                pdfFile = File(getPath(pdfUri,this) ?: "")

                val path = getPath(pdfUri,this)
                val fileName = path?.substring(path.lastIndexOf("/")+1)
                binding.txtFile.text = fileName
            }
        }
    }

    private fun createKunjungan(pengunjungId: RequestBody, file: MultipartBody.Part){
        viewModel.createPengajuan(
            pengunjungId,
            file!!).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Berhasil mengunggah file", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AntrianActivity::class.java)
                    finish()
                }
                State.ERROR -> {
                    progressDialog.dismiss()
                    MaterialDialog(this).show {
                        title(R.string.terjadi_kesalahan)
                        message(text = it.message ?: "Terjadi kesalahan")
                        negativeButton(R.string.batal) {
                        }
                    }
                }
                State.LOADING -> {
                    progressDialog.show();
                }
            }

        }
    }

    private fun File?.toMultipartBody(name: String = "pdf"): MultipartBody.Part? {
        if (this == null) return null
        val reqFile: RequestBody = this.asRequestBody("application/pdf".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(name, this.name, reqFile)
    }

}