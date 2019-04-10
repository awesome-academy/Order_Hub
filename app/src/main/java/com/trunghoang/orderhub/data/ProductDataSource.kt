package com.trunghoang.orderhub.data

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.Product

interface ProductDataSource {
    fun getProducts(): MutableLiveData<APIResponse<List<Product>>>
}
