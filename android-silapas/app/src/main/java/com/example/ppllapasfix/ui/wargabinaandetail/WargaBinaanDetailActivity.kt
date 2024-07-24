package com.example.ppllapasfix.ui.wargabinaandetail

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityWargaBinaanDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class WargaBinaanDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWargaBinaanDetailBinding

    companion object {
        const val STATUS = "STATUS"
        const val NAME = "NAME"
        const val TYPE = "TYPE"
        const val KATEGORI = "KATEGORI"
        const val TANGGAL = "TANGGAL"
    }

    var status = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWargaBinaanDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        status = intent.getBooleanExtra(STATUS, false)
        val name = intent.getStringExtra(NAME)
        val type = intent.getStringExtra(TYPE)
        val kategori = intent.getStringExtra(KATEGORI)
        val tanggal = intent.getStringExtra(TANGGAL)

        binding.tvNama.text = name
        binding.tvJenis.text = type
        binding.tvKategori.text = kategori

        val formatter = SimpleDateFormat("yyyy-MM-dd")

        if(tanggal != null){
            // harusnya item.tanggaL_pengajuan adalah tanggal 2/3
            val twoPerThreeDate: Date = formatter.parse(tanggal)

            val submissionDate = twoPerThreeDate
            // atur 1 bulan sebelum tanggal 2/3
            submissionDate.month = submissionDate.month - 1

            val simpleDateFormat = SimpleDateFormat("dd MMMM yyy")
            val formated = simpleDateFormat.format(submissionDate)

            binding.tvDate.text = formated
        }

        if(status){
            binding.tvStatus.text = "Warga binaan ini sudah dapat mengajukan integrasi. Silakan unduh lalu cetak formulir integrasi untuk dibawa ke lapas"
            binding.tvStatus.background = resources.getDrawable(R.drawable.rounded_green_corner)
            binding.btnPengajuan.visibility = View.VISIBLE
        }

        binding.tvBackDisini.setOnClickListener {
            finish()
        }

        binding.btnPengajuan.setOnClickListener {
            download(this, "formulir-integrasi", ".pdf", Environment.DIRECTORY_DOWNLOADS, "https://drive.google.com/u/0/uc?id=1LT0sE8ar0DCqUHfPWmau0d6Jnvxla_kc&export=download")
        }
    }

    // download formulir
    fun download(context: Context, fileName: String, extension: String, destinationDir: String, url: String){
        val download = context.getSystemService(Context.DOWNLOAD_SERVICE) as  DownloadManager
        val uri = Uri.parse(url)

        val downloadRequest: DownloadManager.Request = DownloadManager.Request(uri)
        Toast.makeText(this, "Proses download dimulai..", Toast.LENGTH_SHORT).show()
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadRequest.setDestinationInExternalFilesDir(context, destinationDir, fileName + extension)
        download.enqueue(downloadRequest)
    }
}