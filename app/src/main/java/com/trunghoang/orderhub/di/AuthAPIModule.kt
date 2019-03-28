package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.service.AuthHttpClient
import com.trunghoang.orderhub.service.ServiceGenerator
import dagger.Module
import dagger.Provides

@Module
class AuthAPIModule {
    @Provides
    fun provideGHNApi() =
        ServiceGenerator().createService(AuthHttpClient())
}
