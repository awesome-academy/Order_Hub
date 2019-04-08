package com.trunghoang.orderhub.ui.orderEditor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.utils.FormatUtils
import kotlinx.android.synthetic.main.item_order_list_product.view.*

class InputProductAdapter(
    private val onRemoveClick: (position: Int) -> Unit
) : RecyclerView.Adapter<InputProductAdapter.ProductViewHolder>() {
    private var products = ArrayList<Product>()
    var editMode: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_order_list_product, parent, false)
        .let {
            ProductViewHolder(it, onRemoveClick)
        }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(products[position], editMode)
    }

    fun setData(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        itemView: View,
        val onRemoveClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun onBind(product: Product, editMode: Boolean) {
            itemView.textQuantity.text = FormatUtils.longToString(product.quantity)
            itemView.textName.text = product.name
            itemView.textPrice.text = FormatUtils.longToString(product.price)
            Glide.with(itemView.context)
                .load(product.photo)
                .into(itemView.imageProductPhoto)
            if (editMode) itemView.buttonRemove.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    onRemoveClick(adapterPosition)
                }
            }
        }
    }
}
