<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateTitipanbarangTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('titipanbarang', function (Blueprint $table) {
            $table->id();

            $table->bigInteger('binaan_id')->unsigned();
            $table->foreign('binaan_id')->references('id')->on('wargabinaan'); 

            $table->bigInteger('pengunjung_id')->unsigned();
            $table->foreign('pengunjung_id')->references('id')->on('pengunjung'); 

            $table->string('kasus');
            $table->string('hub_keluarga');
            $table->string('barang')->nullable();
            $table->integer('uang')->nullable();
            $table->string('nokamar');

            $table->dateTime('tanggal');
            $table->integer('no_antrian');
            $table->string('status');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('titipanbarang');
    }
}
