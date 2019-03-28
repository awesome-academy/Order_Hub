package com.trunghoang.orderhub.service

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL = "https://console.ghn.vn/api/v1/apiv3/"
const val BASE_LOGIN_URL = "https://sso.ghn.vn/"

object EndPoint {
    const val LOGIN = "ssoLogin"
}

object APIConfig {
    const val APP_ID = "apiv3"
}

interface GHNApi {
    @FormUrlEncoded
    @POST(EndPoint.LOGIN)
    fun login(
        @Query(LoginAPI.APP_QUERY) app: String = APIConfig.APP_ID,
        @Field(LoginAPI.APP_ID_KEY) appId: String = APIConfig.APP_ID,
        @Field(LoginAPI.EMAIL_KEY) email: String,
        @Field(LoginAPI.PASSWORD_KEY) password: String
    ): Single<String>
}

interface LoginAPI {
    companion object {
        const val APP_QUERY = "app"
        const val APP_ID_KEY = "appId"
        const val EMAIL_KEY = "username"
        const val PASSWORD_KEY = "password"
    }
}
