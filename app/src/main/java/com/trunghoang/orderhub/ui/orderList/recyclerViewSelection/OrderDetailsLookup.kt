package com.trunghoang.orderhub.ui.orderList.recyclerViewSelection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.orderhub.ui.orderList.OrderAdapter

class OrderDetailsLookup(
    private val recyclerView: RecyclerView
) : ItemDetailsLookup<String>() {
    override fun getItemDetails(e: MotionEvent): ItemDetailsLookup.ItemDetails<String>? =
        recyclerView.findChildViewUnder(e.x, e.y)
            ?.let {
                (recyclerView.getChildViewHolder(it)
                        as OrderAdapter.OrderViewHolder).getItemDetails()
            }
}
