<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateKunjunganTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('kunjungan', function (Blueprint $table) {
            $table->id();

            $table->bigInteger('binaan_id')->unsigned();
            $table->foreign('binaan_id')->references('id')->on('wargabinaan'); 

            $table->bigInteger('pengunjung_id')->unsigned();
            $table->foreign('pengunjung_id')->references('id')->on('pengunjung'); 
            $table->string('hub_keluarga');

            $table->dateTime('tanggal');
            $table->integer('no_antrian');
            $table->string('status');

            $table->string('keterangan')->nullable();
            $table->string('bukti_vaksin');
        });
    }

    public function down()
    {
        Schema::dropIfExists('kunjungan');
    }
}
