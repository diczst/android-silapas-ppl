<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class kunjungan extends Model{
    use HasFactory;

    protected $table = 'kunjungan';
    public $timestamps = false;
    protected $fillable = [
        'binaan_id',
        'pengunjung_id',
        'hub_keluarga',
        'tanggal',
        'no_antrian',
        'status',
        'keterangan',
        'bukti_vaksin'
    ];

	
	
	
	
	
	
	
	


}
