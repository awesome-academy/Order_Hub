package com.trunghoang.orderhub.ui.orderList

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.utils.FormatUtils
import kotlinx.android.synthetic.main.item_order_list.view.*
import javax.inject.Inject

class OrderAdapter @Inject constructor() : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    var orders: List<Order> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list, parent, false)
        .let {
            OrderViewHolder(it)
        }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindView(orders[position])
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
                textCod.text = FormatUtils.formatCurrency(order.cod)
            }
        }
    }
}
