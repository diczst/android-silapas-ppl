<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Pengunjung extends Model{
    use HasFactory;

    protected $table = 'pengunjung';
    public $timestamps = false;
    protected $fillable = [
        'nik',
        'nama',
        'no_handphone',
        'jenkel',
        'alamat',
        'password',
        'image',
    ];
}
