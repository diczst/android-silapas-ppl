<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateWargabinaanTable extends Migration
{

    public function up()
    {
        Schema::create('wargabinaan', function (Blueprint $table) {
           $table->id();

           $table->bigInteger('pengunjung_id')->unsigned()->nullable();
           $table->foreign('pengunjung_id')->references('id')->on('pengunjung'); 

           $table->string('nama');

           // contoh : pidana umum, pidana khusus
           $table->string('kategori');

           // format seperti : 1 tahun 2 bulan
           $table->string('lama_pidana');

           $table->string('jenis_kejahatan');

           $table->date('tanggal_mulai')->nullable();
           $table->date('tanggal_berakhir')->nullable();

           // 2/3 masa pidana (tanggal bisa mengajukan integrasi)
           $table->date('tanggal_pengajuan')->nullable();
        });
    }

    public function down()
    {
        Schema::dropIfExists('wargabinaan');
    }
}
