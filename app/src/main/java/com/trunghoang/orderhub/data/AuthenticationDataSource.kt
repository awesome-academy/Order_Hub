package com.trunghoang.orderhub.data

import io.reactivex.Single

interface AuthenticationDataSource {
    fun authenticate(email: String, password: String): Single<String>
}
