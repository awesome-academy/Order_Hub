package com.trunghoang.orderhub.ui.mainScreen

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.ui.orderList.OrderListFragment
import com.trunghoang.orderhub.ui.orderList.OrderListFragmentScope
import com.trunghoang.orderhub.ui.orderList.OrderListModule
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named
import javax.inject.Scope

@Module(includes = [InjectMainScreenViewModel::class])
abstract class MainScreenModule {
    @ContributesAndroidInjector(modules = [OrderListModule::class])
    @OrderListFragmentScope
    abstract fun contributeOrderListFragment(): OrderListFragment
}

@Module
class InjectMainScreenViewModel {
    @Provides
    @MainScreenFragmentScope
    @Named(MainScreenViewModel.NAME)
    fun provideViewModel(
        mainScreenFragment: MainScreenFragment,
        viewModelFactory: ViewModelFactory
    ) =
        ViewModelProviders.of(mainScreenFragment, viewModelFactory).get(
            MainScreenViewModel::class.java
        )
}

@Scope
@Retention
annotation class MainScreenFragmentScope
