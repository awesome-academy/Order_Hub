package com.trunghoang.orderhub.data.remote

import com.trunghoang.orderhub.data.AuthenticationDataSource
import com.trunghoang.orderhub.service.GHNApi

class AuthenticationRemoteDataSource constructor(private val ghnApi: GHNApi) :
    AuthenticationDataSource {
    override fun authenticate(email: String, password: String) =
        ghnApi.login(email = email, password = password)
}

