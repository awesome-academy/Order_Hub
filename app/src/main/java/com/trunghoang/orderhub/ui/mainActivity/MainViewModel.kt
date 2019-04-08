package com.trunghoang.orderhub.ui.mainActivity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.utils.EventWrapper
import javax.inject.Inject

class MainViewModel @Inject constructor(private val context: Context) :
    ViewModel() {
    companion object {
        const val NAME = "MainViewModel"
        const val PREF_TOKEN = "com.trunghoang.orderhub.TOKEN"
        const val PREF_FILE = "com.trunghoang.orderhub.PREF_FILE"
        const val NO_STRING = ""
    }
    var tokenEvent: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    var orderEditorEvent: MutableLiveData<EventWrapper<String>> = MutableLiveData()
    var toolbarInfo = MutableLiveData<ToolbarInfo>()

    init {
        getSharedPref()
    }

    private fun getSharedPref() {
        tokenEvent.value = EventWrapper(
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
                .getString(PREF_TOKEN, NO_STRING)
        )
    }

    fun saveSharedPref(newToken: String?) {
        newToken?.let {
            with(
                context.getSharedPreferences(
                    PREF_FILE,
                    Context.MODE_PRIVATE
                ).edit()
            ) {
                putString(PREF_TOKEN, it)
                apply()
            }
            tokenEvent.value = EventWrapper(it)
        }
    }

    fun removeSharedPref() {
        with(
            context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE).edit()
        ) {
            remove(PREF_TOKEN)
            apply()
        }
        tokenEvent.value = EventWrapper(NO_STRING)
    }
}
