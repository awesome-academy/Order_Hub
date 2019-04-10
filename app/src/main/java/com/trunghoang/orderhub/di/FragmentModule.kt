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
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorFragment
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorModule
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorScope
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
}
