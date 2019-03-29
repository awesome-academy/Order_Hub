package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.ui.mainActivity.MainActivity
import com.trunghoang.orderhub.ui.mainActivity.MainActivityModule
import com.trunghoang.orderhub.ui.mainActivity.MainActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @MainActivityScope
    abstract fun contributeMainActivity(): MainActivity
}
