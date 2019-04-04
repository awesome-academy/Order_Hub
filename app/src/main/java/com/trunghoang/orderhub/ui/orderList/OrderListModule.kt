package com.trunghoang.orderhub.ui.orderList

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

@Module
class OrderListModule {
    @Provides
    @OrderListFragmentScope
    @Named(OrderListViewModel.NAME)
    fun provideViewModel(
        orderListFragment: OrderListFragment,
        viewModelFactory: ViewModelFactory
    ) = ViewModelProviders.of(orderListFragment, viewModelFactory).get(
        OrderListViewModel::class.java
    )
}

@Scope
@Retention
annotation class OrderListFragmentScope
