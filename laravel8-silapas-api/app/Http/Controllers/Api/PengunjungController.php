<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Pengunjung;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class PengunjungController extends Controller{

    public function store(Request $request){
        $validasi = Validator::make($request->all(), [
            'nik' => 'required|unique:pengunjung',
            'nama' => 'required',
            'no_handphone' => 'required|unique:pengunjung',
            'jenkel' => 'required',
            'alamat' => 'required',
            'password' => 'required|min:6',
            'image' => 'required'
        ]);

        if ($validasi->fails()) {
            return $this->error($validasi->errors()->first());
        }

         $image = $request->image->getClientOriginalName();  

         // format nama dengan menghilangkan spasi
         $image = str_replace(' ', '', $image);

         // membuat agar nama file tidak ada yang sama saat terupload
         $image = date('Hs').rand(1,999) . "_" . $image;

         // ambil nama file
         $fileName = $image;

         // simpan image yang diupload ke public/user
         // (jangan lupa php artisan storage:link terlebih dahulu untuk mengakses gambar)

         // contoh akses gambar : localhost:8000/storage/user/namafile.extensi
         
         $request->image->storeAs('public/user', $image);

        $pengunjung = Pengunjung::create(array_merge($request->all(), [
            'password' => bcrypt($request->password),
            'image' => $fileName
        ]));
        return $this->success($pengunjung);
    }

    public function show($id){
        $pengunjung = Pengunjung::where('id', $id)->first();
        return $this->success($pengunjung);
    }


    // login
    public function login(Request $request){
        $validation = Validator::make($request->all(), [
            'nik' => 'required',
            'password' => 'required',
        ]);

        if ($validation->fails()) {
            return $this->error($validation->errors()->first());
        }

        $pengunjung = Pengunjung::where('nik', $request->nik)->first();
        if($pengunjung){
            if(password_verify($request->password, $pengunjung->password)){
                return $this->success($pengunjung);
            } else {
                return $this->error('Password salah');
            }
            return $this->success($pengunjung, "Selamat datang $pengunjung->nama");
        }
        return $this->error("NIK atau password salah");
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
