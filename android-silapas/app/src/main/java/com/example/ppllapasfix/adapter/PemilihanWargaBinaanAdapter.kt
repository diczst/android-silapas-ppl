package com.example.ppllapasfix.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ppllapasfix.data.model.WargaBinaan
import com.example.ppllapasfix.databinding.ItemWargaBinaanBinding
import com.example.ppllapasfix.ui.wargabinaandetail.WargaBinaanDetailActivity
import com.example.ppllapasfix.ui.formulirkunjungan.KunjunganActivity
import com.example.ppllapasfix.ui.formulirtitipan.TitipanActivity
import com.example.ppllapasfix.ui.pengajuan.PengajuanActivity
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class PemilihanWargaBinaanAdapter() :
    RecyclerView.Adapter<PemilihanWargaBinaanAdapter.ViewHolder>() {
    var data = ArrayList<WargaBinaan>()
    var state = ""
    inner class ViewHolder(private val itemBinding: ItemWargaBinaanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: WargaBinaan, position: Int) {
            itemBinding.apply {
                tvNamaItem.text = item.nama
                tvTypeItem.text = item.jenis_kejahatan
                var status = false

                if(state == "integrasi"){
                    val formatter = SimpleDateFormat("yyyy-MM-dd")

                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)

                    // secara default month yang diambil adalah h-1 bulan dari sekarang
                    // jika sekarang bulan oktober, bulan month akan bernilai september
                    // jika Calender.MONTH tidak ditambah dengan 1

//                    val month = c.get(Calendar.MONTH) + 1
                    val month = c.get(Calendar.MONTH) + 1
                    val day = c.get(Calendar.DATE)
                    val strCurrent = "$year-$month-$day"

                    // testing only
                    // val date1 = "2023-05-18"
                    // val current: Date = formatter.parse(date1)

                    // formatted date
                    val current: Date = formatter.parse(strCurrent)

                    if(item.tanggal_pengajuan != null){
                        // harusnya item.tanggaL_pengajuan adalah tanggal 2/3
                        val twoPerThreeDate: Date = formatter.parse(item.tanggal_pengajuan)

                        val submissionDate = twoPerThreeDate
                        // atur 1 bulan sebelum tanggal 2/3
                        submissionDate.month = submissionDate.month - 1

                        if (current >= submissionDate) {
                            tvStatus.visibility = View.VISIBLE
                            status = true
                        }
                    }
                    tvTypeItem.visibility = View.VISIBLE
                }

                if(state == "pengajuan"){
                    val formatter = SimpleDateFormat("yyyy-MM-dd")

                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)

                    // secara default month yang diambil adalah h-1 bulan dari sekarang
                    // jika sekarang bulan oktober, bulan month akan bernilai september
                    // jika Calender.MONTH tidak ditambah dengan 1

//                    val month = c.get(Calendar.MONTH) + 1
                    val month = c.get(Calendar.MONTH) + 1
                    val day = c.get(Calendar.DATE)
                    val strCurrent = "$year-$month-$day"

                    // testing only
                    // val date1 = "2023-05-18"
                    // val current: Date = formatter.parse(date1)

                    // formatted date
                    val current: Date = formatter.parse(strCurrent)

                    if(item.tanggal_pengajuan != null){
                        // harusnya item.tanggaL_pengajuan adalah tanggal 2/3
                        val twoPerThreeDate: Date = formatter.parse(item.tanggal_pengajuan)

                        val submissionDate = twoPerThreeDate
                        // atur 1 bulan sebelum tanggal 2/3
                        submissionDate.month = submissionDate.month - 1

                        if (current >= submissionDate) {
                            tvStatus.visibility = View.VISIBLE
                            status = true
                        }
                    }
                    tvTypeItem.visibility = View.VISIBLE
                }

                if (state == "kunjungan"){
                    tvTypeItem.visibility = View.VISIBLE
                }

                root.setOnClickListener {
                    if(state == "kunjungan"){
                        val intent = Intent(root.context, KunjunganActivity::class.java)
                        intent.putExtra(KunjunganActivity.WARGA_BINAAN_ID, item.id)
                        intent.putExtra(KunjunganActivity.NAME,item.nama)
                        intent.putExtra(KunjunganActivity.TYPE, item.jenis_kejahatan)
                        root.context.startActivity(intent)
                        (root.context as Activity).finish()
                    } else if(state == "titipan") {
                        val intent = Intent(root.context, TitipanActivity::class.java)
                        intent.putExtra(TitipanActivity.WARGA_BINAAN_ID, item.id)
                        intent.putExtra(TitipanActivity.NAME,item.nama)
                        intent.putExtra(TitipanActivity.TYPE, item.jenis_kejahatan)
                        root.context.startActivity(intent)
                        (root.context as Activity).finish()
                    } else if(state == "pengajuan"){
                        val intent = Intent(root.context, PengajuanActivity::class.java)
                        intent.putExtra(PengajuanActivity.STATUS, status)
                        intent.putExtra(PengajuanActivity.WARGA_BINAAN_ID, item.id)
                        intent.putExtra(PengajuanActivity.NAME,item.nama)
                        root.context.startActivity(intent)
                        (root.context as Activity).finish()
                    }
                    else {
                        val intent = Intent(root.context, WargaBinaanDetailActivity::class.java)
                        intent.putExtra(WargaBinaanDetailActivity.STATUS, status)
                        intent.putExtra(WargaBinaanDetailActivity.NAME,item.nama)
                        intent.putExtra(WargaBinaanDetailActivity.TANGGAL, item.tanggal_pengajuan)
                        intent.putExtra(WargaBinaanDetailActivity.KATEGORI,item.kategori)
                        intent.putExtra(WargaBinaanDetailActivity.TYPE, item.jenis_kejahatan)
                        root.context.startActivity(intent)
                    }

                }

            }

        }
    }

    fun addItems(items: List<WargaBinaan>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWargaBinaanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}