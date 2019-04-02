package com.trunghoang.orderhub.model

import androidx.annotation.IntDef
import com.trunghoang.orderhub.model.OrderStatusDef.COD
import com.trunghoang.orderhub.model.OrderStatusDef.COMPLETED
import com.trunghoang.orderhub.model.OrderStatusDef.RETRY
import com.trunghoang.orderhub.model.OrderStatusDef.RETURNING
import com.trunghoang.orderhub.model.OrderStatusDef.SHIPMENT
import com.trunghoang.orderhub.model.OrderStatusDef.SHIPPING
import com.trunghoang.orderhub.model.OrderStatusDef.TAKEN
import com.trunghoang.orderhub.model.OrderStatusDef.WAITING

object OrderStatusDef{
    const val WAITING = 0
    const val SHIPMENT = 1
    const val TAKEN = 2
    const val SHIPPING = 3
    const val RETRY = 4
    const val RETURNING = 5
    const val COD = 6
    const val COMPLETED = 7
}

@Retention(AnnotationRetention.SOURCE)
@IntDef(WAITING, SHIPMENT, TAKEN, SHIPPING, RETRY, RETURNING, COD, COMPLETED)
annotation class OrderStatus
