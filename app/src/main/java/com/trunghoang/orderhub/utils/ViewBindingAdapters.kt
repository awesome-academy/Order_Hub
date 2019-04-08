package com.trunghoang.orderhub.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide

@BindingAdapter("longNumber")
fun EditText.bindLongNumber(longNumber: MutableLiveData<Long>) {
    val s = FormatUtils.longToString(longNumber.value)
    if (text.toString() != s) {
        setText(s)
    }
}

@BindingAdapter("longNumberAttrChanged")
fun EditText.setNumberListener(listener: InverseBindingListener) {
    addTextChangedListener( object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(
            s: CharSequence?,
            start: Int,
            count: Int,
            after: Int
        ) {}

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int,
            count: Int
        ) {
            listener.onChange()
            setSelection(text.length)
        }
    })
}

@InverseBindingAdapter(attribute = "longNumber", event = "longNumberAttrChanged")
fun EditText.getTextNumber(): Long? {
    return FormatUtils.stringToLong(text.toString()) as Long?
}

@BindingAdapter("image")
fun ImageView.bindImage(photoUrl: MutableLiveData<String>) {
    Glide.with(context)
        .load(photoUrl.value)
        .into(this)
}
