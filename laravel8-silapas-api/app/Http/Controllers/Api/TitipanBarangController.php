<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\TitipanBarang;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class TitipanBarangController extends Controller
{

    public function store(Request $request)
    {
        $validasi = Validator::make($request->all(), [
            'binaan_id' => 'required',
            'pengunjung_id'=> 'required',
            'hub_keluarga'=> 'required',
            'tanggal'=> 'required',
            'kasus'=> 'required',
            'nokamar' => 'required',
        ]);

        if ($validasi->fails()) {
            return $this->error($validasi->errors()->first());
        }

         /* Catatan
          menggunakan fungsi sql date() karena kolom tanggal pada tabel kunjungan bertipe data datetime, sehingga harus dikonver ke date untuk mencari datenya saja tanpa time */

         // generate no. antrian
         $no_antrian = TitipanBarang::
         select(DB::raw('date(tanggal)'), DB::raw('MAX(no_antrian) as no_antrian_terakhir'))
         ->where(DB::raw('date(tanggal)'), $request->tanggal)
         ->groupBy(DB::raw('date(tanggal)'))
         ->first();

         // beri nomor antrian pertama jika belum ada yang mengantri
         // pada tanggal x
         $no_antrian_final = 1;

         // kalau dalam suatu tanggal sudah ada pengunjung yang mengantri
         // generate nomor antrian pengunjung ini n + 1
         if($no_antrian != null){
            $no_antrian_final = $no_antrian->no_antrian_terakhir + 1;
        } 

         // untuk menampilkan query sql sebagai string untuk debugging
        //  ->toSql();

        $isAntrianBelumDiproses = TitipanBarang::
         select('pengunjung_id', DB::raw('date(tanggal)'), 'no_antrian', 'status')
         ->where('pengunjung_id', $request->pengunjung_id)
         ->where('status', 'Belum diproses')
         ->first();
         
         if($isAntrianBelumDiproses != null){
            return $this->error("Anda sudah memiliki nomor antrian titipan barang yang saat ini belum diproses. Silahkan melakukan pembatalan atau penuhi nomor antrian anda yang belum diproses");
         } else {

            // tidak ada nomor antrian yang belum diproses maka beri nomor antrian masukkan ke database
            $kunjungan = TitipanBarang::create(array_merge($request->all(), [
            'status' => 'Belum diproses',
            'no_antrian' => $no_antrian_final,
        ]));
            return $this->success($kunjungan);
         }
    }

    public function show($id){
        $titipan = TitipanBarang::where('pengunjung_id', $id)
                              ->where('status','Belum diproses')->first();
        return $this->success($titipan);
    }


    public function update(Request $request, $id){
        $titipanBarang = TitipanBarang::where('pengunjung_id', $id)->
        where('status', 'Belum diproses')
        ->first();
        if($titipanBarang) {
            $titipanBarang->update($request->all());
            return $this->success($titipanBarang);
        } else {
            return $this->error("Kunjungan tidak ditemukan");
        }
    }

    // response
    public function success($data, $message = "success") {
        return response()->json([
            'code' => 200,
            'message' => $message,
            'data' => $data
        ]);
    }

    public function error($message) {
        return response()->json([
            'code' => 400,
            'message' => $message
        ], 400);
    }

}
