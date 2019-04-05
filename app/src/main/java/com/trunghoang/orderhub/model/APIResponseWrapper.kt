package com.trunghoang.orderhub.model

data class APIResponseWrapper<T>(
    val code: Int,
    val msg: String,
    val data: T
) {
    companion object {
        const val CODE_SUCCESS = 1
    }
}
