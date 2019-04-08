package com.trunghoang.orderhub.ui.orderEditor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.ShippingInfoRepository
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.model.GHNApiResponse
import javax.inject.Inject

class OrderEditorViewModel @Inject constructor(
    private val shippingInfoRepo: ShippingInfoRepository
) : ViewModel() {
    companion object {
        const val NAME = "OrderEditorViewModel"
    }

    var districtsResponse = MutableLiveData<APIResponse<List<District>>>()
    var wardsResponse = MutableLiveData<APIResponse<GHNApiResponse.Wards>>()
    var feeResponse = MutableLiveData<APIResponse<GHNApiResponse.Fee>>()
    var servicesResponse =
        MutableLiveData<APIResponse<List<GHNApiResponse.Service>>>()
    var district = MutableLiveData<District>()
    var serviceId = MutableLiveData<Int>()

    fun getDistricts(districtsRequest: GHNApiRequest.Districts) {
        districtsResponse = shippingInfoRepo.getDistricts(districtsRequest)
    }

    fun getWards(wardsRequest: GHNApiRequest.Wards) {
        wardsResponse = shippingInfoRepo.getWards(wardsRequest)
    }

    fun findServices(servicesRequest: GHNApiRequest.Services) {
        servicesResponse = shippingInfoRepo.findServices(servicesRequest)
    }

    fun calculateFee(feeRequest: GHNApiRequest.Fee) {
        feeResponse = shippingInfoRepo.calculateFee(feeRequest)
    }
}
