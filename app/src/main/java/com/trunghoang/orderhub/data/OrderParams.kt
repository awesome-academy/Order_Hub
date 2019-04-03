package com.trunghoang.orderhub.data

import com.google.firebase.firestore.Query
import com.trunghoang.orderhub.model.OrderStatus

class OrderParams<T>(
    @OrderStatus val status: Int,
    val sortedBy: String = FIELD_CREATED_TIME,
    val sortDirection: Query.Direction = Query.Direction.ASCENDING,
    var startAt: T? = null,
    val limitSize: Int = DEFAULT_LIMIT
) {
    companion object {
        const val FIELD_STATUS = "status"
        const val FIELD_CREATED_TIME = "createdTime"
        const val FIELD_NAME = "name"
        const val DEFAULT_LIMIT = 3
    }
}
