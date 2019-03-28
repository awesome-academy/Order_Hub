package com.trunghoang.orderhub.data

import io.reactivex.Single

interface DataSource {
    fun authenticate(email: String, password: String): Single<String>
}