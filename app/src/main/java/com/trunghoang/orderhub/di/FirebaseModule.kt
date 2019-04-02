package com.trunghoang.orderhub.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {
    @Singleton
    @Provides
    fun provideFirebaseInstance() = FirebaseFirestore.getInstance()
}
