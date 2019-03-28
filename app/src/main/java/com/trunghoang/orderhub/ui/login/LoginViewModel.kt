package com.trunghoang.orderhub.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.AuthenticationRepository
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.service.ServiceGenerator.Companion.KEY_ATTR
import com.trunghoang.orderhub.service.ServiceGenerator.Companion.SELECTOR_TOKEN
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    var loginResponse: MutableLiveData<APIResponse<String>> = MutableLiveData()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun authenticate(email: String, password: String) {
        compositeDisposable.add(
            authenticationRepository.authenticate(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loginResponse.value =
                        APIResponse.loading()
                }
                .subscribe(
                    { result ->
                        loginResponse.value =
                            Jsoup.parse(result)
                                .select(SELECTOR_TOKEN)
                                ?.first()?.attr(KEY_ATTR)
                                .let {
                                    APIResponse.success(
                                        it ?: APIResponse.NO_VALUE
                                    )
                                }
                    },
                    { error ->
                        loginResponse.value =
                            APIResponse.error(error)
                    }
                )
        )
    }
}
