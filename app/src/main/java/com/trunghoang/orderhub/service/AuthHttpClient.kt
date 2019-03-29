package com.trunghoang.orderhub.service

import okhttp3.OkHttpClient

class AuthHttpClient {
    companion object {
        const val SEE_OTHER = 303
        const val URL_ACCOUNT = "https://api.ghn.vn/home/account"
        const val HEADER_LOCATION = "Location"
        const val HEADER_COOKIE = "Cookie"
        const val HEADER_SET_COOKIE = "Set-Cookie"
    }

    fun create(): OkHttpClient =
        OkHttpClient.Builder()
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
            .build()
}
