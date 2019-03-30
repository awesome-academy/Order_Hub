package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.data.remote.OrderRemoteDataSource
import com.trunghoang.orderhub.model.OrderStatus
import javax.inject.Inject

class OrderRepository @Inject constructor(private val remote: OrderRemoteDataSource) :
    OrderDataSource {
    override fun getOrders(
        @OrderStatus
        status: Int
    ) = remote.getOrders(status)
}
