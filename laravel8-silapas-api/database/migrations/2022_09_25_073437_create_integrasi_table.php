<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateIntegrasiTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('integrasi', function (Blueprint $table) {
            $table->id();

            $table->bigInteger('binaan_id')->unsigned();
            $table->foreign('binaan_id')->references('id')->on('wargabinaan'); 

            $table->bigInteger('pengunjung_id')->unsigned();
            $table->foreign('pengunjung_id')->references('id')->on('pengunjung'); 
        
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('integrasi');
    }
}
