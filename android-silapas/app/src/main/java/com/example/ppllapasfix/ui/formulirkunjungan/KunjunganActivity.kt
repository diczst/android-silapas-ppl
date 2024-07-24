package com.example.ppllapasfix.ui.formulirkunjungan

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import com.afollestad.materialdialogs.MaterialDialog
import com.example.ppllapasfix.R
import com.example.ppllapasfix.databinding.ActivityKunjunganBinding
import com.example.ppllapasfix.network.State
import com.example.ppllapasfix.ui.antrian.AntrianActivity
import com.example.ppllapasfix.utils.MyPreference
import com.example.ppllapasfix.utils.dayToBahasaIndonesia
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
import java.text.SimpleDateFormat
import java.util.*

class KunjunganActivity : AppCompatActivity() {

    companion object{
        const val WARGA_BINAAN_ID = "WARGA_BINAAN_ID"
        const val NAME: String = "NAME"
        const val TYPE: String = "TYPE"
    }

    private lateinit var binding : ActivityKunjunganBinding
    private val viewModel: KunjunganViewmodel by inject()
    private var fileImage: File? = null
    private lateinit var progressDialog: ProgressDialog

    // didapatkan dari pemilihan sebelumnya
    private var wargaBinaanId: Int = 0
    private var name: String? = ""
    private var type: String? = ""
    private var dayName: String? = ""

    private var finalDate: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKunjunganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)

        wargaBinaanId = intent.getIntExtra(WARGA_BINAAN_ID,0)
        name = intent.getStringExtra(NAME)
        type = intent.getStringExtra(TYPE)

        binding.namaWbp.setText(name)
        binding.actvJenisPidana.setText(type)

        val hubungan = listOf("Ayah Kandung", "Ibu Kandung", "Istri", "Anak", "Paman", "Bibi", "Kakek", "Nenek", "Lainnya")
        val adaptor = ArrayAdapter(this, R.layout.list_item, hubungan)
        binding.actvHubungan.setAdapter(adaptor)

        // Waktu saat ini (berfungsi untuk mengisi argument)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // proses mengambil data tanggal dari user
        val dpd = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            // Untuk konversi tanggal ke nama hari (bahasa indonesia)
            val sdf = SimpleDateFormat("EEEE", Locale.US)
            val formatedDate = Date(selectedYear, selectedMonth, selectedDay - 1)
            dayName = sdf.format(formatedDate).dayToBahasaIndonesia()

            // menampilkan nama hari yang dipilih (konversi tanggal ke nama hari)
            val sdfFull = SimpleDateFormat("dd MMMM", Locale.US);
            binding.actvTanggal.setText("$dayName, ${sdfFull.format(Date(selectedYear,selectedMonth,selectedDay))} $selectedYear")

            // get current time
            val sdfTime = SimpleDateFormat("hh:mm:ss", Locale.US)
            val currentDate = sdfTime.format(Date())

            finalDate = "$selectedYear-${selectedMonth+1}-$selectedDay"

        }, year, month, day)

        binding.tvBackDisini.setOnClickListener{
            finish()
        }

        binding.imgBuktiVaksin.setOnClickListener {
            pickImage()
        }

        binding.actvTanggal.setOnClickListener {
            val now = System.currentTimeMillis() - 1000
            dpd.datePicker.minDate = now
//            dpd.datePicker.maxDate = (now+(1000*60*60*24*6))
            dpd.datePicker.dayOfMonth.toDrawable()
            dpd.show()
        }

        binding.btnProsesAntrian.setOnClickListener {
            prosesAntrian()
        }
    }

    private fun prosesAntrian(){
        if (binding.actvHubungan.text!!.isEmpty()){
            binding.actvHubungan.setError("Pilih hubungan keluarga")
            binding.actvHubungan.requestFocus()
            return
        } else {
            binding.actvHubungan.error = null
        }

        if (binding.actvTanggal.text!!.isEmpty()){
            binding.actvTanggal.error = "Tanggal tidak boleh kosong"
            binding.actvTanggal.requestFocus()
            return
        } else {
            binding.actvTanggal.error = null

        }

        if (binding.etKeterangan.text!!.isEmpty()){
            binding.etKeterangan.error = "Keterangan tidak boleh kosong"
            binding.etKeterangan.requestFocus()
            return
        } else {
            binding.etKeterangan.error = null
        }

        if(fileImage == null){
            Toast.makeText(this, "Harap upload bukti vaksin booster", Toast.LENGTH_LONG).show()
            return
        }

        // kalau form sudah tidak kosong lagi hilangkan error yang ada

        val idWargaBinaan = wargaBinaanId.toString().toRequestBody("text/plain".toMediaType())
        val pengunjungId = MyPreference.getUser()?.id.toString().toRequestBody("text/plain".toMediaType())
        val hubKeluarga = binding.actvHubungan.text.toString().toRequestBody("text/plain".toMediaType())
        val keterangan = binding.etKeterangan.text.toString().toRequestBody("text/plain".toMediaType())
        val convertedFinalDate = finalDate?.toRequestBody("text/plain".toMediaType())

        if(fileImage != null){
            // name ada nama request dari image
            val file = fileImage.toMultipartBody("bukti_vaksin")

            if(type == "Pidana Umum"){
                if(dayName == "Senin" || dayName == "Rabu" || dayName == "Sabtu"){
                    createKunjungan(idWargaBinaan, pengunjungId, hubKeluarga,  convertedFinalDate!!, keterangan, file!!)
                } else {
                    MaterialDialog(this).show {
                        title(R.string.terjadi_kesalahan)
                        message(text = "Maaf kunjungan untuk warga binaan pidana umum hanya bisa dilakukan pada hari Senin, Rabu, dan Sabtu ")
                        negativeButton(R.string.ok) {
                        }
                    }
//                    Toast.makeText(this, "Maaf kunjungan untuk warga binaan pidana umum hanya bisa dilakukan pada hari Senin, Rabu, dan Sabtu ", Toast.LENGTH_LONG).show()
                }
            } else {
                if(dayName=="Selasa"|| dayName=="Kamis") {
                    createKunjungan(idWargaBinaan, pengunjungId, hubKeluarga,  convertedFinalDate!!, keterangan, file!!)
                }
                else{
                    MaterialDialog(this).show {
                        title(R.string.terjadi_kesalahan)
                        message(text = "Maaf kunjungan untuk warga binaan pidana khusus hanya bisa dilakukan pada hari Selasa dan Kamis ")
                        negativeButton(R.string.ok) {
                        }
                    }
//                    Toast.makeText(this, "Maaf kunjungan untuk warga binaan pidana khusus hanya bisa dilakukan pada hari Selasa dan Kamis ", Toast.LENGTH_LONG).show()

                }

            }
        }
    }



    private fun createKunjungan(idWargaBinaan: RequestBody, pengunjungId: RequestBody, hubKeluarga: RequestBody, convertedFinalDate: RequestBody, keterangan: RequestBody, file: MultipartBody.Part){
        viewModel.createKunjungan(
            idWargaBinaan,
            pengunjungId,
            hubKeluarga,
            convertedFinalDate,
            keterangan,
            file!!).observe(this) {
            when(it.state){
                State.SUCCESS -> {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Berhasil mendapatkan nomor antrian : ${it.data?.data?.no_antrian}", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, AntrianActivity::class.java)
                    intent.putExtra(AntrianActivity.STATE,"kunjungan")
                    startActivity(intent)
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
//                    Toast.makeText(this, it.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
                State.LOADING -> {
                    progressDialog.show();
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
            Picasso.get().load(uri).into(binding.imgBuktiVaksin)
        }
    }

}