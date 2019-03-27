package com.trunghoang.orderhub.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ServiceGenerator {
    companion object {
        const val SELECTOR_TOKEN = "#APIKey"
        const val KEY_ATTR = "value"
    }

    fun createService(httpClient: OkHttpClient): GHNApi = Retrofit.Builder()
        .baseUrl(BASE_LOGIN_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient)
        .build()
        .create(GHNApi::class.java)
}
