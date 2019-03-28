package com.trunghoang.orderhub.data.remote

import com.trunghoang.orderhub.data.DataSource
import com.trunghoang.orderhub.service.LoginService
import javax.inject.Inject

class AuthenticationRemoteDataSource @Inject constructor(): DataSource {
    override fun authenticate(email: String, password: String) =
        LoginService().start(email, password)
}
