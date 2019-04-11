package com.trunghoang.orderhub.ui.orderDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.ui.orderEditor.InputProductAdapter
import kotlinx.android.synthetic.main.item_order_list_product.view.*

class InputProductDetailAdapter(
    private val onRemoveClick: (position: Int) -> Unit
) : InputProductAdapter(onRemoveClick) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list_product, parent, false)
        .let {
            InputProductDetailViewHolder(it, onRemoveClick)
        }

    class InputProductDetailViewHolder(
        itemView: View,
        onRemoveClick: (position: Int) -> Unit
    ): InputProductAdapter.InputProductViewHolder(
        itemView,
        onRemoveClick
    ) {
        override fun bindEditModeUI() {
            itemView.buttonRemove.visibility = View.GONE
        }
    }
}
