package com.trunghoang.orderhub.ui.orderList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.OrderRepository
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.model.OrderStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrderListViewModel @Inject constructor(private val repo: OrderRepository) :
    ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var ordersResponse: MutableLiveData<APIResponse<List<Order>>> =
        MutableLiveData()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun getOrders(@OrderStatus status: Int) {
        compositeDisposable.add(
            repo.getOrders(status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    ordersResponse.value =
                        APIResponse(EnumStatus.LOADING, null, null)
                }
                .subscribe(
                    {
                        ordersResponse.value =
                            APIResponse(EnumStatus.SUCCESS, it, null)
                    },
                    {
                        ordersResponse.value =
                            APIResponse(EnumStatus.ERROR, null, it)
                    })
        )
    }
}
