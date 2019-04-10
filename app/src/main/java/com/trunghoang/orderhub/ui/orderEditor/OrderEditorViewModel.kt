package com.trunghoang.orderhub.ui.orderEditor

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.OrderRepository
import com.trunghoang.orderhub.data.ShippingInfoRepository
import com.trunghoang.orderhub.model.*
import java.util.*
import javax.inject.Inject

class OrderEditorViewModel @Inject constructor(
    private val shippingInfoRepo: ShippingInfoRepository,
    private val orderRepository: OrderRepository
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
    var ward = MutableLiveData<Ward>()
    var id = MutableLiveData<String>()
    var createdTime = MutableLiveData<Long>()
    var lastModified = MutableLiveData<Long>()
    var status = MutableLiveData<Int>()
    var serviceId = MutableLiveData<Int>()
    var name = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var fee = MutableLiveData<Long>().apply { value = 0 }
    var discount = MutableLiveData<Long>().apply { value = 0 }
    var saveOrderResponse = MutableLiveData<APIResponse<Boolean>>()
    var orderResponse = MutableLiveData<APIResponse<Order>>()
    var districtFullName = MutableLiveData<String>()
    var wardName = MutableLiveData<String>()
    var products = MutableLiveData<MutableList<Product>>().apply {
        value = ArrayList()
    }
    var cod = MediatorLiveData<Long>().apply {
        addSource(products) { calculateCOD() }
        addSource(fee) { calculateCOD() }
        addSource(discount) { calculateCOD() }
    }

    fun getOrder(id: String) {
        orderResponse = orderRepository.getOrder(id)
    }

    fun updateOrder(order: Order?) {
        order?.let {
            id.value = it.id
            name.value = it.name
            createdTime.value = it.createdTime
            lastModified.value = it.lastModified
            status.value = it.status
            mobile.value = it.mobile
            address.value = it.address
            districtFullName.value = it.district?.fullName
            wardName.value = it.ward?.name
            fee.value = it.fee
            discount.value = it.discount
            cod.value = it.cod
            products.value = it.products as MutableList<Product>
        }
    }

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

    fun saveOrder() {
        val now = Date().time
        val order: Order =
            id.value?.let {
                Order(
                    id = it,
                    createdTime = createdTime.value ?: now,
                    lastModified = now,
                    name = name.value,
                    mobile = mobile.value,
                    address = address.value,
                    district = district.value,
                    ward = ward.value,
                    fee = fee.value,
                    discount = discount.value,
                    cod = cod.value,
                    products = products.value
                )
            } ?: run {
                Order(
                    id = UUID.randomUUID().toString(),
                    createdTime = now,
                    lastModified = now,
                    name = name.value,
                    mobile = mobile.value,
                    address = address.value,
                    district = district.value,
                    ward = ward.value,
                    fee = fee.value,
                    discount = discount.value,
                    cod = cod.value,
                    products = products.value
                )
            }
        saveOrderResponse = orderRepository.saveOrder(order)
    }

    fun addProduct(product: Product?) {
        product?.let {
            products.value?.add(it)
            products.value = products.value
        }
    }

    fun removeProduct(position: Int) {
        products.value?.removeAt(position)
        products.value = products.value
    }

    private fun calculateCOD() {
        cod.value = 0
        products.value?.let {
            for (product in it) {
                cod.value =
                    cod.value!! + (product.price ?: 0) * (product.quantity ?: 0)
            }
        }
        cod.value = cod.value!! + (fee.value ?: 0) - (discount.value ?: 0)
    }
}

