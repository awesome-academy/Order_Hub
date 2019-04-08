package com.trunghoang.orderhub.service

import com.trunghoang.orderhub.model.APIResponseWrapper
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.model.GHNApiResponse
import io.reactivex.Single
import retrofit2.http.*

const val BASE_URL = "https://console.ghn.vn/api/v1/apiv3/"
const val BASE_LOGIN_URL = "https://sso.ghn.vn/"

object EndPoint {
    const val LOGIN = "ssoLogin"
    const val GET_DISTRICTS = "GetDistricts"
    const val GET_WARDS = "GetWards"
    const val CALCULATE_FEE = "CalculateFee"
    const val FIND_AVAILABLE_SERVICES = "FindAvailableServices"
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

    @POST(EndPoint.GET_DISTRICTS)
    fun getDistricts(
        @Body districtsRequest: GHNApiRequest.Districts
    ): Single<APIResponseWrapper<List<District>>>

    @POST(EndPoint.GET_WARDS)
    fun getWards(
        @Body wardsRequest: GHNApiRequest.Wards
    ): Single<APIResponseWrapper<GHNApiResponse.Wards>>

    @POST(EndPoint.CALCULATE_FEE)
    fun calculateFee(
        @Body feeRequest: GHNApiRequest.Fee
    ): Single<APIResponseWrapper<GHNApiResponse.Fee>>

    @POST(EndPoint.FIND_AVAILABLE_SERVICES)
    fun findServices(
        @Body servicesRequest: GHNApiRequest.Services
    ): Single<APIResponseWrapper<List<GHNApiResponse.Service>>>
}

interface LoginAPI {
    companion object {
        const val APP_QUERY = "app"
        const val APP_ID_KEY = "appId"
        const val EMAIL_KEY = "username"
        const val PASSWORD_KEY = "password"
    }
}
