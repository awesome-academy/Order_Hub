package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.data.remote.AuthenticationRemoteDataSource
import dagger.Component

@Component(modules = [AuthAPIModule::class])
interface AuthRemoteComponent {
    fun inject(authenticationRemoteDataSource: AuthenticationRemoteDataSource)
}
