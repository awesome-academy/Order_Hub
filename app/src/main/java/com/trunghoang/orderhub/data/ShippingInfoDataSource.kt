package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.model.APIResponseWrapper
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import io.reactivex.Single

interface ShippingInfoDataSource {
    fun getDistricts(
        districtsRequest: GHNApiRequest.Districts
    ): Single<APIResponseWrapper<List<District>>>
}
