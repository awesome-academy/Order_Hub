package com.trunghoang.orderhub.ui.orderList

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.utils.FormatUtils
import kotlinx.android.synthetic.main.item_order_list.view.*
import javax.inject.Inject

class OrderAdapter @Inject constructor() :
    PagedListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list, parent, false)
        .let {
            OrderViewHolder(it)
        }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindView(it)
        }
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(order: Order) {
            with(itemView) {
                textCreatedTime.text = DateUtils.formatDateTime(
                    context,
                    order.createdTime,
                    DateUtils.FORMAT_SHOW_DATE
                )
                textName.text = order.name
                textCod.text = FormatUtils.longToString(order.cod)
            }
        }
    }

    companion object {
        val OrderDiffCallback = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(
                oldItem: Order,
                newItem: Order
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Order,
                newItem: Order
            ) = oldItem == newItem
        }
    }
}
