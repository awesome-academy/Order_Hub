package com.trunghoang.orderhub.ui.mainScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.model.OrderStatusDef
import com.trunghoang.orderhub.utils.EventWrapper
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(): ViewModel() {
    companion object {
        const val NAME = "MainScreenViewModel"
    }
    var orderStatusEvent = MutableLiveData<EventWrapper<Int>>().apply {
        value = EventWrapper(OrderStatusDef.WAITING)
    }
}