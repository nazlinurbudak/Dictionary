package com.nazlinurbudak.dictionary.common

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    data class Error<T>(val exception: String) : NetworkResult<T>()
}

data class Error(
    val code: Int,
    val type: String,
    val message: String
)
