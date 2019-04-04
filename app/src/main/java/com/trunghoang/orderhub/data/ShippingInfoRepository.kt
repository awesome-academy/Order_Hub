package com.trunghoang.orderhub.data

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.data.remote.ShippingInfoRemoteDataSource
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import com.trunghoang.orderhub.service.APIDisposableGenerator
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ShippingInfoRepository @Inject constructor(
    private val shippingInfoRemote: ShippingInfoRemoteDataSource
) {
    fun getDistricts(
        districtsRequest: GHNApiRequest.Districts,
        districtsResponse: MutableLiveData<APIResponse<List<District>>>,
        compositeDisposable: CompositeDisposable
    ) {
        compositeDisposable.add(
            APIDisposableGenerator.createSingleDisposable(
                shippingInfoRemote.getDistricts(districtsRequest),
                districtsResponse
            )
        )
    }
}
