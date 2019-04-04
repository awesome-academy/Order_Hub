package com.trunghoang.orderhub.ui.orderEditor

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

@Module
class OrderEditorModule {
    @Provides
    @OrderEditorScope
    @Named(OrderEditorViewModel.NAME)
    fun provideViewModel(orderEditorFragment: OrderEditorFragment, viewModelFactory: ViewModelFactory) =
            ViewModelProviders.of(orderEditorFragment, viewModelFactory).get(OrderEditorViewModel::class.java)
}

@Scope
@Retention
annotation class OrderEditorScope
