package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.data.remote.AuthenticationRemoteDataSource
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(private val authenticationRemote: AuthenticationRemoteDataSource) {
    fun callLogin(email: String, password: String) =
        authenticationRemote.authenticate(email, password)
}
