package com.trunghoang.orderhub.ui.mainActivity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val context: Context) : ViewModel() {
    companion object {
        const val PREF_TOKEN = "com.trunghoang.orderhub.TOKEN"
        const val PREF_FILE = "com.trunghoang.orderhub.PREF_FILE"
    }

    var token: MutableLiveData<String> = MutableLiveData()

    fun getSharedPref() {
        token.value =
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
                .getString(PREF_TOKEN, null)
    }

    fun saveSharedPref(newToken: String?) {
        if (newToken != null) {
            with(
                context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit()
            ) {
                putString(PREF_TOKEN, newToken)
                apply()
            }
            token.value = newToken
        }
    }

    fun removeSharedPref() {
        with(
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit()
        ) {
            remove(PREF_TOKEN)
            apply()
        }
        token.value = null
    }
}
