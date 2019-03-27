package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.login.LoginFragmentModule
import com.trunghoang.orderhub.ui.login.LoginFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    @LoginFragmentScope
    abstract fun contributeLoginFragment(): LoginFragment
}
