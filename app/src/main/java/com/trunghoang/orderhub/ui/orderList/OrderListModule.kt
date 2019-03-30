package com.trunghoang.orderhub.ui.orderList

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class OrderListModule {
    @Provides
    fun provideViewModel(
        orderListFragment: OrderListFragment,
        viewModelFactory: ViewModelFactory
    ) = ViewModelProviders.of(orderListFragment, viewModelFactory).get(
        OrderListViewModel::class.java
    )
    @Provides
    fun provideLinearLayout(context: Context) = LinearLayoutManager(context)
}
