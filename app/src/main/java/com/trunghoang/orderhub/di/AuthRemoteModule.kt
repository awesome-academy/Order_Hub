package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.data.remote.AuthenticationRemoteDataSource
import com.trunghoang.orderhub.service.AuthHttpClient
import com.trunghoang.orderhub.service.GHNApi
import com.trunghoang.orderhub.service.ServiceGenerator
import dagger.Module
import dagger.Provides

@Module
class AuthRemoteModule {
    @Provides
    fun provideAuthRemoteDataSource(): AuthenticationRemoteDataSource =
        AuthenticationRemoteDataSource(
            ServiceGenerator().createService(
                AuthHttpClient().create()
            )
        )
}
