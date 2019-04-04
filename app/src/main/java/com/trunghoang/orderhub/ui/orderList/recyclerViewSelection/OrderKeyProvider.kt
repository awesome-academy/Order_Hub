package com.trunghoang.orderhub.ui.orderList.recyclerViewSelection

import androidx.recyclerview.selection.ItemKeyProvider
import com.trunghoang.orderhub.ui.orderList.OrderAdapter

class OrderKeyProvider(
    private val adapter: OrderAdapter
) : ItemKeyProvider<String>(SCOPE_MAPPED) {

    override fun getKey(position: Int) = adapter.getItemKey(position)

    override fun getPosition(
        key: String
    ): Int = adapter.getPositionFromKey(key)
}
