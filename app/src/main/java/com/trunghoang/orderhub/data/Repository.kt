package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.data.remote.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(private val remote: RemoteDataSource) {
    fun callLogin(email: String, password: String) =
        remote.callLogin(email, password)
}
