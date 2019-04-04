package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.service.ServiceGenerator
import dagger.Module
import dagger.Provides

@Module
class GHNApiModule {
    @Provides
    fun provideGHNApi() = ServiceGenerator().createGhnService()
}
