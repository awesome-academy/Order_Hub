package com.trunghoang.orderhub.adapter

import android.content.Context
import android.view.View
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.utils.standardize
import kotlinx.android.synthetic.main.item_suggest_list.view.*

class ProductsAdapter(
    context: Context, itemLayout: Int, data: List<Product>
) : BaseSuggestAdapter<Product>(context, itemLayout, data) {
    override fun setTextItem(view: View, item: Product) {
        view.textSuggestItem.text = item.name
    }

    override fun getFilter() = ProductFilter()

    inner class ProductFilter : BaseSuggestFilter() {
        override fun checkItem(item: Product, pattern: String): Boolean {
            return item.name?.standardize()?.contains(pattern) ?: false
        }

        override fun convertResultToString(resultValue: Any?) =
            (resultValue as Product).name
    }
}
