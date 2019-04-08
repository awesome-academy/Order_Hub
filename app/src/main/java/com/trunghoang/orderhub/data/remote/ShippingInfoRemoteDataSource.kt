package com.trunghoang.orderhub.data.remote

import com.trunghoang.orderhub.data.ShippingInfoDataSource
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.service.GHNApi
import javax.inject.Inject

class ShippingInfoRemoteDataSource @Inject constructor(private val ghnApi: GHNApi) :
    ShippingInfoDataSource {
    override fun getDistricts(districtsRequest: GHNApiRequest.Districts) =
        ghnApi.getDistricts(districtsRequest)

    override fun getWards(wardsRequest: GHNApiRequest.Wards) =
        ghnApi.getWards(wardsRequest)

    override fun calculateFee(feeRequest: GHNApiRequest.Fee) =
        ghnApi.calculateFee(feeRequest)

    override fun findServices(servicesRequest: GHNApiRequest.Services) =
        ghnApi.findServices(servicesRequest)
}
