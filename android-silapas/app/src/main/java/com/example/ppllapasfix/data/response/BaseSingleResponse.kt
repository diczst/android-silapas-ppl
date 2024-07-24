package com.example.ppllapasfix.data.response

data class BaseSingleResponse<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null
)
