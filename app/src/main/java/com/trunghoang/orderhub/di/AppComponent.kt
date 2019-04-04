package com.trunghoang.orderhub.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        AuthRemoteModule::class,
        FirebaseModule::class,
        GHNApiModule::class]
)
interface AppComponent {
    fun inject(appController: AppController)

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(application: Application): Builder

        abstract fun build(): AppComponent
    }
}
