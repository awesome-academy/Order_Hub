package com.trunghoang.orderhub.data

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.Order

interface OrderDataSource {
    fun saveOrder(order: Order): MutableLiveData<APIResponse<Boolean>>
    fun getOrder(id: String): MutableLiveData<APIResponse<Order>>
}
