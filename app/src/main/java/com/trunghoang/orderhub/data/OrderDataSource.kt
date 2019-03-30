package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.model.OrderStatus
import io.reactivex.Observable

interface OrderDataSource {
    fun getOrders(@OrderStatus status: Int): Observable<List<Order>>
}
