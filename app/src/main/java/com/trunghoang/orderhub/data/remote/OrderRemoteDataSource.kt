package com.trunghoang.orderhub.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.OrderDataSource
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.model.OrderStatus
import io.reactivex.Observable
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(private val db: FirebaseFirestore) :
    OrderDataSource {
    companion object {
        const val COL_ORDERS = "orders"
        const val FIELD_STATUS = "status"
    }

    override fun getOrders(@OrderStatus status: Int) =
        Observable.create<List<Order>> {
            val listenerRegistration = db.collection(COL_ORDERS)
                .whereEqualTo(FIELD_STATUS, status)
                .addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        it.onError(exception)
                        return@addSnapshotListener
                    }
                    val orders = ArrayList<Order>()
                    for (order in snapshot!!) {
                        orders.add(order.toObject(Order::class.java))
                    }
                    it.onNext(orders)
                }
            it.setCancellable { listenerRegistration.remove() }
        }
}
