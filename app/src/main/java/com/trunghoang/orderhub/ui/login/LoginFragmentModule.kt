package com.trunghoang.orderhub.ui.login

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class LoginFragmentModule {
    @Provides
    @LoginFragmentScope
    fun provideViewModel(
        loginFragment: LoginFragment,
        viewModelFactory: ViewModelFactory
    ) = ViewModelProviders.of(loginFragment, viewModelFactory)
        .get(LoginViewModel::class.java)
}

@Scope
@Retention
annotation class LoginFragmentScope
