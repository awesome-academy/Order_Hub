package com.trunghoang.orderhub.ui.orderDetail

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorViewModel
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

@Module
class OrderDetailModule {
    @Provides
    @OrderDetailScope
    @Named(OrderEditorViewModel.NAME)
    fun provideViewModel(
        orderDetailFragment: OrderDetailFragment,
        viewModelFactory: ViewModelFactory
    ) = ViewModelProviders.of(orderDetailFragment, viewModelFactory)
        .get(OrderEditorViewModel::class.java)
}

@Scope
@Retention
annotation class OrderDetailScope
