package com.trunghoang.orderhub.utils

import android.content.Context
import android.widget.Toast
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.OrderStatus
import com.trunghoang.orderhub.model.OrderStatusDef.COD
import com.trunghoang.orderhub.model.OrderStatusDef.COMPLETED
import com.trunghoang.orderhub.model.OrderStatusDef.RETRY
import com.trunghoang.orderhub.model.OrderStatusDef.RETURNING
import com.trunghoang.orderhub.model.OrderStatusDef.SHIPMENT
import com.trunghoang.orderhub.model.OrderStatusDef.SHIPPING
import com.trunghoang.orderhub.model.OrderStatusDef.TAKEN
import com.trunghoang.orderhub.model.OrderStatusDef.WAITING
import retrofit2.http.HEAD

fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.getOrderStatusText(@OrderStatus status: Int) = when (status) {
    WAITING -> getString(R.string.item_status_1)
    SHIPMENT -> getString(R.string.item_status_2)
    TAKEN -> getString(R.string.item_status_3)
    SHIPPING -> getString(R.string.item_status_4)
    RETRY -> getString(R.string.item_status_5)
    RETURNING -> getString(R.string.item_status_6)
    COD -> getString(R.string.item_status_7)
    COMPLETED -> getString(R.string.item_status_8)
    else -> getString(R.string.app_name)
}

fun Context.getOrderStatusFromId(itemId: Int) = when (itemId) {
    R.id.item_status_1 -> WAITING
    R.id.item_status_2 -> SHIPMENT
    R.id.item_status_3 -> TAKEN
    R.id.item_status_4 -> SHIPPING
    R.id.item_status_5 -> RETRY
    R.id.item_status_6 -> RETURNING
    R.id.item_status_7 -> COD
    R.id.item_status_8 -> COMPLETED
    else -> null
}
