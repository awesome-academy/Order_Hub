package com.trunghoang.orderhub.ui.orderEditor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.ShippingInfoRepository
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.model.GHNApiRequest
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderEditorViewModel @Inject constructor(
    private val shippingInfoRepo: ShippingInfoRepository
) : ViewModel() {
    companion object {
        const val NAME = "OrderEditorViewModel"
    }
    var compositeDisposable = CompositeDisposable()
    var districtsResponse = MutableLiveData<APIResponse<List<District>>>()
    var district = MutableLiveData<District>()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun getDistricts(districtsRequest: GHNApiRequest.Districts) {
        shippingInfoRepo.getDistricts(
            districtsRequest,
            districtsResponse,
            compositeDisposable
        )
    }
}
