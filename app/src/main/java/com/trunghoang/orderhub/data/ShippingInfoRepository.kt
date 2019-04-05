package com.trunghoang.orderhub.data

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.data.remote.ShippingInfoRemoteDataSource
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.utils.toLiveData
import javax.inject.Inject

class ShippingInfoRepository @Inject constructor(
    private val shippingInfoRemote: ShippingInfoRemoteDataSource
) {
    fun getDistricts(
        districtsRequest: GHNApiRequest.Districts
    ): MutableLiveData<APIResponse<List<District>>> =
        shippingInfoRemote.getDistricts(districtsRequest).toLiveData()
                as MutableLiveData<APIResponse<List<District>>>
}
