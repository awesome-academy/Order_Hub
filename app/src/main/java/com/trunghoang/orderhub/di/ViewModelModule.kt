package com.trunghoang.orderhub.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.trunghoang.orderhub.ui.inputProduct.InputProductViewModel
import com.trunghoang.orderhub.ui.login.LoginViewModel
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.ui.mainScreen.MainScreenViewModel
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorViewModel
import com.trunghoang.orderhub.ui.orderList.OrderListViewModel
import com.trunghoang.orderhub.utils.ViewModelFactory
import com.trunghoang.orderhub.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderListViewModel::class)
    abstract fun bindOrderListViewModel(orderListViewModel: OrderListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindMainScreenViewModel(mainScreenViewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(OrderEditorViewModel::class)
    abstract fun bindOrderEditorViewModel(orderEditorViewModel: OrderEditorViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InputProductViewModel::class)
    abstract fun bindInputProductViewModel(inputProductViewModel: InputProductViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory
}
