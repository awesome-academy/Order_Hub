package com.trunghoang.orderhub.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.OrderDataSource
import com.trunghoang.orderhub.data.firebase.FirebaseKeys.COL_ORDERS
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.utils.toLiveData
import io.reactivex.Observable
import javax.inject.Inject

class OrderFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) : OrderDataSource {
    override fun saveOrder(order: Order) =
        Observable.create<Boolean> { emitter ->
            db.collection(COL_ORDERS)
                .document(order.id)
                .set(order)
                .addOnSuccessListener {
                    emitter.onNext(true)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
                .addOnCompleteListener {
                    emitter.onComplete()
                }
        }.toLiveData() as MutableLiveData<APIResponse<Boolean>>

    override fun getOrder(id: String) = Observable.create<Order> { emitter ->
        db.collection(COL_ORDERS)
            .document(id)
            .get()
            .addOnSuccessListener {
                it.toObject(Order::class.java)?.let { order ->
                    emitter.onNext(order)
                }
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
            .addOnCompleteListener {
                emitter.onComplete()
            }
    }.toLiveData() as MutableLiveData<APIResponse<Order>>
}
