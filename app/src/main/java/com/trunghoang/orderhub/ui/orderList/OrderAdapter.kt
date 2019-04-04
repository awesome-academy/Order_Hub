package com.trunghoang.orderhub.ui.orderList

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.utils.FormatUtils
import kotlinx.android.synthetic.main.item_order_list.view.*

class OrderAdapter(
    private val onDetailClick: (order: Order) -> Unit,
    private val onProductsClick: (itemView: View, order: Order) -> Unit,
    private val inSelectionMode: () -> Unit
) :
    PagedListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback) {
    var tracker: SelectionTracker<String>? = null
        set(value) {
            field = value?.apply {
                addObserver(
                    object : SelectionTracker.SelectionObserver<String>() {
                        override fun onSelectionChanged() {
                            super.onSelectionChanged()
                            selection?.size()?.let {
                                if (it != 0) inSelectionMode()
                            }
                        }
                    })
            }
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list, parent, false)
        .let {
            OrderViewHolder(it, onDetailClick, onProductsClick)
        }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindView(
                it,
                tracker?.isSelected(getItemKey(position))
            )
        }
    }

    fun getItemKey(position: Int) = getItem(position)?.id

    fun getPositionFromKey(key: String) = currentList?.map { it.id }
        ?.indexOf(key)
        ?: RecyclerView.NO_POSITION

    class OrderViewHolder(
        itemView: View,
        val onDetailClick: (order: Order) -> Unit,
        val onProductsClick: (view: View, order: Order) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        var itemKey: String? = null

        fun bindView(
            order: Order,
            isSelected: Boolean?
        ) {
            itemKey = order.id
            with(itemView) {
                textCreatedTime.text = DateUtils.formatDateTime(
                    context,
                    order.createdTime,
                    DateUtils.FORMAT_SHOW_DATE
                )
                textName.text = order.name
                textCod.text = FormatUtils.longToString(order.cod)
                buttonDetail.setOnClickListener {
                    onDetailClick(order)
                }
                buttonProducts.setOnClickListener {
                    onProductsClick(itemView, order)
                }
                isSelected?.let {
                    if (it) {
                        cardOrderItem.strokeWidth =
                            resources.getDimensionPixelSize(R.dimen.item_order_stroke)
                        imageCheck.visibility = View.VISIBLE
                    } else {
                        cardOrderItem.strokeWidth = 0
                        imageCheck.visibility = View.GONE
                    }
                }
            }
        }

        fun getItemDetails() =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getSelectionKey() = itemKey
                override fun getPosition() = adapterPosition
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
