<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class WargaBinaan extends Model{
    use HasFactory;

    protected $table = 'wargabinaan';
    public $timestamps = false;
    protected $fillable = [
        'pengunjung_id',
    ];
}
