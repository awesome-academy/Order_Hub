package com.trunghoang.orderhub.service

import androidx.lifecycle.MutableLiveData
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.APIResponseWrapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class APIDisposableGenerator {
    companion object {
        @JvmStatic
        fun <T>createSingleDisposable(
            observable: Single<APIResponseWrapper<T>>,
            response: MutableLiveData<APIResponse<T>>
        ): Disposable = observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                response.value = APIResponse.loading()
            }
            .map {
                if (it.code == APIResponseWrapper.CODE_SUCCESS) {
                    it.data
                } else {
                    throw Exception(it.msg)
                }
            }
            .subscribe(
                {
                    response.value = APIResponse.success(it)
                },
                {
                    response.value = APIResponse.error(it)
                }
            )
    }
}
