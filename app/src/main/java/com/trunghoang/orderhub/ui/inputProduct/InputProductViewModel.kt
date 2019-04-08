package com.trunghoang.orderhub.ui.inputProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputProductViewModel : ViewModel() {
    companion object {
        const val NAME = "InputProductViewModel"
        const val DEFAULT_QUANTITY = 1L
        const val DEFAULT_PRICE = 0L
    }
    val quantity = MutableLiveData<Long>().apply {
        value = DEFAULT_QUANTITY
    }
    val name = MutableLiveData<String>()
    val price = MutableLiveData<Long>().apply {
        value = DEFAULT_PRICE
    }
    val photo = MutableLiveData<String>()
}
