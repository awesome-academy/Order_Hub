package com.trunghoang.orderhub.ui.inputProduct

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Scope

@Module
class InputProductModule {
    @Provides
    @InputProductScope
    @Named(InputProductViewModel.NAME)
    fun provideViewModel(
        inputProductFragment: InputProductFragment,
        viewModelFactory: ViewModelFactory
    ) = ViewModelProviders.of(inputProductFragment, viewModelFactory)
        .get(InputProductViewModel::class.java)
}

@Scope
@Retention
annotation class InputProductScope
