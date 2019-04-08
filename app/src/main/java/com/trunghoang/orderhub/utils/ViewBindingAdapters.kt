package com.trunghoang.orderhub.utils

import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.format.DateUtils
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.trunghoang.orderhub.R

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

@BindingAdapter("toggleTextEditMode")
fun EditText.bindTextEditMode(editMode: MutableLiveData<Boolean>) {
    editMode.value?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable = if (it) View.FOCUSABLE else View.NOT_FOCUSABLE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!it) {
                setBackgroundColor(
                    resources.getColor(
                        R.color.color_input_background,
                        null
                    )
                )
            } else {
                setBackgroundResource(R.drawable.abc_edit_text_material)
            }
        }
        inputType = if (!it) {
            InputType.TYPE_NULL
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
        }
    }
}

@BindingAdapter("toggleNumberEditMode")
fun EditText.bindNumberEditMode(editMode: MutableLiveData<Boolean>) {
    editMode.value?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable = if (it) View.FOCUSABLE else View.NOT_FOCUSABLE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!it) {
                setBackgroundColor(
                    resources.getColor(
                        R.color.color_input_background,
                        null
                    )
                )
            } else {
                setBackgroundResource(R.drawable.abc_edit_text_material)
            }
        }
        inputType = if (!it) {
            InputType.TYPE_NULL
        } else {
            InputType.TYPE_CLASS_NUMBER
        }
    }
}

@BindingAdapter("toggleVisibleEditMode")
fun View.bindVisibleEditMode(editMode: MutableLiveData<Boolean>) {
    editMode.value?.let {
        visibility = if (it) View.VISIBLE else View.GONE
    }
}

@BindingAdapter("toggleGoneEditMode")
fun View.bindGoneEditMode(editMode: MutableLiveData<Boolean>) {
    editMode.value?.let {
        visibility = if (it) View.GONE else View.VISIBLE
    }
}

@BindingAdapter("toggleToolbarEditMode")
fun Toolbar.bindToolbarEditMode(editMode: MutableLiveData<Boolean>) {
    editMode.value?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (it) {
                setBackgroundColor(
                    resources.getColor(
                        R.color.color_primary,
                        null
                    )
                )
            } else {
                setBackgroundColor(
                    resources.getColor(
                        R.color.color_primary_dark,
                        null
                    )
                )
            }
        }
    }
}

@BindingAdapter("status")
fun TextView.bindStatus(status: MutableLiveData<Int>) {
    status.value?.let {
        text = context.getOrderStatusText(it)
    }
}

@BindingAdapter("dateTime")
fun TextView.bindCreatedTime(dateTime: MutableLiveData<Long>) {
    dateTime.value?.let {
        text = DateUtils.getRelativeDateTimeString(
            context,
            it,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE
        )
    }
}
