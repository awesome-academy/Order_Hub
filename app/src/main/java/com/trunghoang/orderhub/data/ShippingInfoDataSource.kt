package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.model.APIResponseWrapper
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.model.GHNApiResponse
import io.reactivex.Single

interface ShippingInfoDataSource {
    fun getDistricts(
        districtsRequest: GHNApiRequest.Districts
    ): Single<APIResponseWrapper<List<District>>>

    fun getWards(
        wardsRequest: GHNApiRequest.Wards
    ): Single<APIResponseWrapper<GHNApiResponse.Wards>>

    fun calculateFee(
        feeRequest: GHNApiRequest.Fee
    ): Single<APIResponseWrapper<GHNApiResponse.Fee>>

    fun findServices(
        servicesRequest: GHNApiRequest.Services
    ): Single<APIResponseWrapper<List<GHNApiResponse.Service>>>
}
