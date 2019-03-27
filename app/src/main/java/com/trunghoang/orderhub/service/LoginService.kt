package com.trunghoang.orderhub.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginService {
    companion object {
        const val SEE_OTHER = 303
        const val URL_ACCOUNT = "https://api.ghn.vn/home/account"
        const val HEADER_LOCATION = "Location"
        const val HEADER_COOKIE = "Cookie"
        const val HEADER_SET_COOKIE = "Set-Cookie"
        const val SELECTOR_TOKEN = "#APIKey"
        const val KEY_ATTR = "value"
    }

    fun start(email: String, password: String) = Retrofit.Builder()
        .baseUrl(BASE_LOGIN_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(OkHttpClient.Builder()
                    .followRedirects(false)
                    .addInterceptor { chain ->
                        val req = chain.request()
                        var res = chain.proceed(req)
                        if (res.code() == SEE_OTHER) {
                            res = chain.proceed(
                                req.newBuilder()
                                    .url(res.header(HEADER_LOCATION)!!)
                                    .build()
                            )
                            res = chain.proceed(
                                req.newBuilder()
                                    .url(URL_ACCOUNT)
                                    .addHeader(
                                        HEADER_COOKIE,
                                        res.header(HEADER_SET_COOKIE)!!
                                    )
                                    .build()
                            )
                        }
                        res
                    }
                    .build())
        .build()
        .create(GhnAPI::class.java)
        .login(email = email, password = password)
}
