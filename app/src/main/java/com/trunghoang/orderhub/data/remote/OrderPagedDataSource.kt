package com.trunghoang.orderhub.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.OrderParams
import com.trunghoang.orderhub.data.OrderParams.Companion.FIELD_STATUS
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.Order
import io.reactivex.disposables.CompositeDisposable

class OrderPagedDataSource<T>(
    private val db: FirebaseFirestore,
    private var orderParams: OrderParams<T>,
    private var compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<T, Order>() {
    companion object {
        const val COL_ORDERS = "orders"
    }

    var progressStatus: MutableLiveData<EnumStatus> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<T>,
        callback: LoadInitialCallback<Order>
    ) {
        progressStatus.postValue(EnumStatus.LOADING)
        getOrders(orderParams, callback)
    }

    override fun loadAfter(
        params: LoadParams<T>,
        callback: LoadCallback<Order>
    ) {
        progressStatus.postValue(EnumStatus.LOADING)
        getOrders(orderParams.apply { startAt = params.key }, callback)
    }

    override fun loadBefore(
        params: LoadParams<T>,
        callback: LoadCallback<Order>
    ) {
    }

    @Suppress("UNCHECKED_CAST")
    override fun getKey(item: Order): T =
        when (orderParams.sortedBy) {
            OrderParams.FIELD_CREATED_TIME -> item.createdTime as T
            else -> item.name as T
        }

    private fun getOrders(
        orderParams: OrderParams<T>,
        loadCallback: LoadCallback<Order>
    ) = db.collection(COL_ORDERS)
        .whereEqualTo(FIELD_STATUS, orderParams.status)
        .orderBy(orderParams.sortedBy, orderParams.sortDirection)
        .startAfter(orderParams.startAt)
        .limit(orderParams.limitSize.toLong())
        .get()
        .addOnSuccessListener {
            val orders = ArrayList<Order>()
            for (snapshot in it.documents) {
                snapshot?.toObject(Order::class.java)
                    ?.apply { id = snapshot.id }
                    ?.let { order ->
                        orders.add(order)
                    }
            }
            progressStatus.postValue(EnumStatus.SUCCESS)
            loadCallback.onResult(orders)
        }
        .addOnFailureListener {
            progressStatus.postValue(EnumStatus.ERROR)
        }
}
