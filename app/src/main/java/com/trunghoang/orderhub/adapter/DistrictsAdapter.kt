package com.trunghoang.orderhub.adapter

import android.content.Context
import android.view.View
import com.trunghoang.orderhub.model.District
import com.trunghoang.orderhub.utils.standardize
import kotlinx.android.synthetic.main.item_suggest_list.view.*

class DistrictsAdapter(context: Context, itemLayout: Int, districts: List<District>) :
    BaseSuggestAdapter<District>(context, itemLayout, districts) {

    override fun setTextItem(view: View, item: District) {
        view.textSuggestItem?.text = item.fullName
    }

    override fun getFilter() = DistrictFilter()

    inner class DistrictFilter : BaseSuggestFilter() {
        override fun checkItem(item: District, pattern: String): Boolean {
            return item.fullName.standardize().contains(pattern)
        }

        override fun convertResultToString(resultValue: Any?) =
            (resultValue as District).fullName
    }
}
