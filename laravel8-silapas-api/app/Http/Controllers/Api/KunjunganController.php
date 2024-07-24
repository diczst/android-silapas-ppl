<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\kunjungan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class KunjunganController extends Controller{
    public function index(){

    }

    public function store(Request $request){
        $validasi = Validator::make($request->all(), [
            'binaan_id' => 'required',
            'pengunjung_id'=> 'required',
            'hub_keluarga'=> 'required',
            'tanggal'=> 'required',
            'keterangan'=> 'required',
            'bukti_vaksin' => 'required'
        ]);

        if ($validasi->fails()) {
            return $this->error($validasi->errors()->first());
        }

         $image = $request->bukti_vaksin->getClientOriginalName();  

         // format nama dengan menghilangkan spasi
         $image = str_replace(' ', '', $image);

         // membuat agar nama file tidak ada yang sama saat terupload
         $image = date('Hs').rand(1,999) . "_" . $image;
         $fileName = $image;

         // contoh akses gambar : localhost:8000/storage/user/namafile.extensi
         
         $request->bukti_vaksin->storeAs('public/user', $image);

         /* Catatan
          menggunakan fungsi sql date() karena kolom tanggal pada tabel kunjungan bertipe data datetime, sehingga harus dikonver ke date untuk mencari datenya saja tanpa time */

         // generate no. antrian
         $no_antrian = kunjungan::
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

        $isAntrianBelumDiproses = kunjungan::
         select('pengunjung_id', DB::raw('date(tanggal)'), 'no_antrian', 'status')
         ->where('pengunjung_id', $request->pengunjung_id)
         ->where('status', 'Belum diproses')
         ->first();
         
         if($isAntrianBelumDiproses != null){
            return $this->error("Anda sudah memiliki nomor antrian kunjungan yang saat ini belum diproses. Silahkan melakukan pembatalan atau penuhi nomor antrian anda yang belum diproses");
         } else {

            // tidak ada nomor antrian yang belum diproses maka beri nomor antrian masukkan ke database
            $kunjungan = kunjungan::create(array_merge($request->all(), [
            'status' => 'Belum diproses',
            'no_antrian' => $no_antrian_final,
            'bukti_vaksin' => $fileName
        ]));
            return $this->success($kunjungan);
         }

    }

    // first means we get a single object
    // get means we get list of object but only get one data in this function
    public function show($id){
        $kunjungan = kunjungan::where('pengunjung_id', $id)
                              ->where('status','Belum diproses')->first();
        return $this->success($kunjungan);
    }

    public function update(Request $request, $id) {
        $kunjungan = kunjungan::where('pengunjung_id', $id)->
        where('status', 'Belum diproses')
        ->first();
        if($kunjungan) {
            $kunjungan->update($request->all());
            return $this->success($kunjungan);
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
