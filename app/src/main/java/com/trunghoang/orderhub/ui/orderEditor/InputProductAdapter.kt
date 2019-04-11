package com.trunghoang.orderhub.ui.orderEditor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.adapter.BaseRecyclerAdapter
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.utils.FormatUtils
import kotlinx.android.synthetic.main.item_order_list_product.view.*

open class InputProductAdapter(
    private val onRemoveClick: (position: Int) -> Unit
) : BaseRecyclerAdapter<Product>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Product> = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list_product, parent, false)
        .let {
            InputProductViewHolder(it, onRemoveClick)
        }

    open class InputProductViewHolder(
        itemView: View,
        private val onRemoveClick: (position: Int) -> Unit
    ) : BaseViewHolder<Product>(itemView) {
        override fun onBind(item: Product) {
            itemView.textQuantity.text = FormatUtils.longToString(item.quantity)
            itemView.textName.text = item.name
            itemView.textPrice.text = FormatUtils.longToString(item.price)
            Glide.with(itemView.context)
                .load(item.photo)
                .into(itemView.imageProductPhoto)
            bindEditModeUI()
        }

        open fun bindEditModeUI() {
            itemView.buttonRemove.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    onRemoveClick(adapterPosition)
                }
            }
        }
    }
}
