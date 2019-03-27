package com.trunghoang.orderhub.data.remote

import com.trunghoang.orderhub.service.LoginService
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {
    fun callLogin(email: String, password: String) =
        LoginService().start(email, password)
}
