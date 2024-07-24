<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TitipanBarang extends Model{
    use HasFactory;

    protected $table = 'titipanbarang';
    public $timestamps = false;
    protected $fillable = [
        'binaan_id',
        'pengunjung_id',
        'hub_keluarga',
        'tanggal',
        'no_antrian',
        'kasus',
        'barang',
        'uang',
        'nokamar',
        'status'
    ];

	
	
	
	
	
	
	
	


}
