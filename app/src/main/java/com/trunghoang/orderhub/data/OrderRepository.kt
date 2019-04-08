package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.data.firebase.OrderFirebaseDataSource
import com.trunghoang.orderhub.model.Order
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val firebaseDataSource: OrderFirebaseDataSource
) : OrderDataSource {
    override fun saveOrder(order: Order) = firebaseDataSource.saveOrder(order)
    override fun getOrder(id: String) = firebaseDataSource.getOrder(id)
}
