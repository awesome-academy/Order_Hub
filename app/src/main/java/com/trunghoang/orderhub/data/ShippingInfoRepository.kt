package com.trunghoang.orderhub.data

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.data.remote.ShippingInfoRemoteDataSource
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.model.GHNApiResponse
import com.trunghoang.orderhub.utils.toLiveData
import javax.inject.Inject

class ShippingInfoRepository @Inject constructor(private val shippingInfoRemote: ShippingInfoRemoteDataSource) {
    fun getDistricts(
        districtsRequest: GHNApiRequest.Districts
    ): MutableLiveData<APIResponse<List<District>>> =
        shippingInfoRemote.getDistricts(districtsRequest).toLiveData()
                as MutableLiveData<APIResponse<List<District>>>

    fun getWards(
        wardsRequest: GHNApiRequest.Wards
    ): MutableLiveData<APIResponse<GHNApiResponse.Wards>> =
        shippingInfoRemote.getWards(wardsRequest).toLiveData()
                as MutableLiveData<APIResponse<GHNApiResponse.Wards>>

    fun calculateFee(
        feeRequest: GHNApiRequest.Fee
    ): MutableLiveData<APIResponse<GHNApiResponse.Fee>> =
        shippingInfoRemote.calculateFee(feeRequest).toLiveData()
                as MutableLiveData<APIResponse<GHNApiResponse.Fee>>

    fun findServices(
        servicesRequest: GHNApiRequest.Services
    ): MutableLiveData<APIResponse<List<GHNApiResponse.Service>>> =
        shippingInfoRemote.findServices(servicesRequest).toLiveData()
                as MutableLiveData<APIResponse<List<GHNApiResponse.Service>>>
}
