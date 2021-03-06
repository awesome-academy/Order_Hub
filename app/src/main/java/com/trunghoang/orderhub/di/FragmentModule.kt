package com.trunghoang.orderhub.di

import com.trunghoang.orderhub.ui.inputProduct.InputProductFragment
import com.trunghoang.orderhub.ui.inputProduct.InputProductModule
import com.trunghoang.orderhub.ui.inputProduct.InputProductScope
import com.trunghoang.orderhub.ui.login.LoginFragment
import com.trunghoang.orderhub.ui.login.LoginFragmentModule
import com.trunghoang.orderhub.ui.login.LoginFragmentScope
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragment
import com.trunghoang.orderhub.ui.mainScreen.MainScreenFragmentScope
import com.trunghoang.orderhub.ui.mainScreen.MainScreenModule
import com.trunghoang.orderhub.ui.orderDetail.OrderDetailFragment
import com.trunghoang.orderhub.ui.orderDetail.OrderDetailModule
import com.trunghoang.orderhub.ui.orderDetail.OrderDetailScope
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorFragment
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorModule
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorScope
import com.trunghoang.orderhub.ui.orderListSelection.OrderListSelectionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    @LoginFragmentScope
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [MainScreenModule::class])
    @MainScreenFragmentScope
    abstract fun contributeMainScreenFragment(): MainScreenFragment

    @ContributesAndroidInjector(modules = [OrderEditorModule::class])
    @OrderEditorScope
    abstract fun contributeOrderEditorFragment(): OrderEditorFragment

    @ContributesAndroidInjector(modules = [InputProductModule::class])
    @InputProductScope
    abstract fun contributeInputProductFragment(): InputProductFragment

    @ContributesAndroidInjector(modules = [OrderDetailModule::class])
    @OrderDetailScope
    abstract fun contributeOrderDetailFragment(): OrderDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderListSelectionFragment(): OrderListSelectionFragment
}
