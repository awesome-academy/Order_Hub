package com.trunghoang.orderhub.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.APIResponseWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

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

fun <T> Single<APIResponseWrapper<T>>.toLiveData(): LiveData<APIResponse<T>> {
    return object : MutableLiveData<APIResponse<T>>() {
        var disposable: Disposable? = null

        override fun onActive() {
            super.onActive()

            disposable = this@toLiveData
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { this.value = APIResponse.loading() }
                .map {
                    if (it.code == APIResponseWrapper.CODE_SUCCESS) {
                        it.data
                    } else {
                        throw Exception(it.msg)
                    }
                }
                .subscribe(
                    {
                        this.value = APIResponse.success(it)
                    },
                    {
                        this.value = APIResponse.error(it)
                    }
                )
        }

        override fun onInactive() {
            disposable?.dispose()
            super.onInactive()
        }
    }
}
