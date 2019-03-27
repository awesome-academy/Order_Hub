package com.trunghoang.orderhub.ui.orderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.OrderParams
import com.trunghoang.orderhub.data.remote.PagedDataSourceFactory
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.utils.DoubleTriggerLiveData
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrderListViewModel @Inject constructor(private val db: FirebaseFirestore) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var dataSourceFactoryLiveData =
        MutableLiveData<PagedDataSourceFactory<*>>()
    private var orderParamsLiveData = MutableLiveData<OrderParams<*>>()
    private var pagedListConfigLiveData =
        Transformations.switchMap(orderParamsLiveData) {
            MutableLiveData<PagedList.Config>().apply {
                value = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(it.limitSize)
                    .setPageSize(it.limitSize)
                    .build()
            }
        }
    var ordersPagedList: LiveData<PagedList<Order>> =
        Transformations.switchMap(DoubleTriggerLiveData(dataSourceFactoryLiveData, pagedListConfigLiveData)) { pair ->
            pair.second?.let { config ->
                LivePagedListBuilder(
                    pair.first as PagedDataSourceFactory<*>,
                    config
                ).build()
            }
        }
    var progressStatus: LiveData<EnumStatus> =
        Transformations.switchMap(dataSourceFactoryLiveData) { factory ->
            Transformations.switchMap(factory.dataSourceLiveData) {
                it.progressStatus
            }
        }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun getOrders(orderParams: OrderParams<out Any>) {
        orderParamsLiveData.value = orderParams
        dataSourceFactoryLiveData.value = when (orderParams.sortedBy) {
            OrderParams.FIELD_CREATED_TIME -> PagedDataSourceFactory<Long>(
                db,
                orderParams as OrderParams<Long>,
                compositeDisposable
            )
            else -> null
        }
    }

    fun refresh() {
        dataSourceFactoryLiveData.value?.dataSourceLiveData?.value?.invalidate()
    }
}
