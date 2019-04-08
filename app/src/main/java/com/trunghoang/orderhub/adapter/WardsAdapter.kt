package com.trunghoang.orderhub.adapter

import android.content.Context
import android.view.View
import com.trunghoang.orderhub.model.Ward
import com.trunghoang.orderhub.utils.standardize
import kotlinx.android.synthetic.main.item_suggest_list.view.*

class WardsAdapter(context: Context, itemLayout: Int, data: List<Ward>) :
    BaseSuggestAdapter<Ward>(context, itemLayout, data) {
    override fun setTextItem(view: View, item: Ward) {
        view.textSuggestItem.text = item.name
    }

    override fun getFilter() = WardFilter()

    inner class WardFilter : BaseSuggestFilter() {
        override fun checkItem(item: Ward, pattern: String): Boolean {
            return item.name?.standardize()?.contains(pattern)?:false
        }

        override fun convertResultToString(resultValue: Any?) =
            (resultValue as Ward).name
    }
}
