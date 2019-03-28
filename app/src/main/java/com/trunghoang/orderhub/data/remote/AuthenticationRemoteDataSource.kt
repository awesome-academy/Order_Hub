package com.trunghoang.orderhub.data.remote

import com.trunghoang.orderhub.data.AuthenticationDataSource
import com.trunghoang.orderhub.service.GHNApi
import javax.inject.Inject

class AuthenticationRemoteDataSource @Inject constructor(private val ghnAPI: GHNApi): AuthenticationDataSource {
    init {
        DaggerAuthRemoteComponent.builder().build().inject(this)
    }
    override fun authenticate(email: String, password: String) =
        ghnAPI.login(email = email, password = password)
}
