package com.trunghoang.orderhub.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.OrderParams
import com.trunghoang.orderhub.model.Order
import io.reactivex.disposables.CompositeDisposable

class PagedDataSourceFactory<T>(
    private val db: FirebaseFirestore,
    private var orderParams: OrderParams<T>,
    private var compositeDisposable: CompositeDisposable
) : DataSource.Factory<T, Order>() {
    var dataSourceLiveData: MutableLiveData<OrderPagedDataSource<T>> =
        MutableLiveData()

    override fun create(): OrderPagedDataSource<T> = OrderPagedDataSource(
        db,
        orderParams,
        compositeDisposable
    ).also {
        dataSourceLiveData.postValue(it)
    }
}
