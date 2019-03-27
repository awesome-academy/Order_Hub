package com.trunghoang.orderhub.di

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    FragmentModule::class,
    ViewModelModule::class])
interface AppComponent {
    fun inject(appController: AppController)
}
