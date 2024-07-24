<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\WargaBinaan;
use Illuminate\Http\Request;

class WargaBinaanController extends Controller
{
    public function index(){
        $wargaBinaan = WargaBinaan::get();
        return $this->success($wargaBinaan);
    }

    public function search($nama){
        $wargaBinaan = WargaBinaan::where('nama', 'like', '%' . $nama . '%')->get();
        return $this->success($wargaBinaan);
    }

 
    public function show($id){
    }


    public function update(Request $request, $id){
        $wargaBinaan = WargaBinaan::where('id', $id)->first();
        if($wargaBinaan){
            $wargaBinaan->update($request->all());
            return $this->success($wargaBinaan);
        } else {
            return $this->error("Warga binaan tidak ditemukan");
        }
    }

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
