<?php

use App\Http\Controllers\Api\KunjunganController;
use App\Http\Controllers\Api\PengunjungController;
use App\Http\Controllers\Api\TitipanBarangController;
use App\Http\Controllers\Api\WargaBinaanController;
use App\Models\kunjungan;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::post('loginpengunjung', [PengunjungController::class, "login"]);

Route::resource('pengunjung', PengunjungController::class);

Route::resource('wargabinaan', WargaBinaanController::class);
Route::get('wargabinaan/search/{nama}', [WargaBinaanController::class, "search"]);

Route::resource('kunjungan', KunjunganController::class);
Route::resource('titipanbarang', TitipanBarangController::class);


Route::put('batalkankunjungan/{pengunjung_id}', [KunjunganController::class, "update"]);
Route::put('batalkantitipan/{pengunjung_id}', [TitipanBarangController::class, "update"]);

