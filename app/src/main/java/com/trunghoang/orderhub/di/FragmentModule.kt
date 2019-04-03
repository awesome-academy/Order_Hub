package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.login.LoginFragmentModule
import com.trunghoang.orderhub.ui.login.LoginFragmentScope
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import com.trunghoang.orderhub.ui.orderList.OrderListFragment
import com.trunghoang.orderhub.ui.orderList.OrderListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    @LoginFragmentScope
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [OrderListModule::class])
    abstract fun contributeOrderListFragment(): OrderListFragment

    @ContributesAndroidInjector
    abstract fun contributeMainScreenFragment(): MainScreenFragment
}
