package com.trunghoang.orderhub.ui.mainActivity

import androidx.lifecycle.ViewModelProviders
import com.trunghoang.orderhub.utils.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Module
class MainActivityModule {
    @Provides
    @MainActivityScope
    fun provideViewModel(
        mainActivity: MainActivity,
        viewModelFactory: ViewModelFactory
    ) =
        ViewModelProviders.of(mainActivity, viewModelFactory)
            .get(MainViewModel::class.java)
}

@Scope
@Retention
annotation class MainActivityScope
