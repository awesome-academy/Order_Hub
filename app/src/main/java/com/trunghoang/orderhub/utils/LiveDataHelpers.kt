package com.trunghoang.orderhub.utils

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

fun <T> MutableLiveData<MutableList<T>>.addToStack(item: T) {
    this.value?.apply {
        add(item)
        notifyObserver()
    }
}

fun <T> MutableLiveData<MutableList<T>>.popStack() {
    if (this.value.isNullOrEmpty()) return
    this.value!!.apply {
        removeAt(lastIndex)
        notifyObserver()
    }
 }
