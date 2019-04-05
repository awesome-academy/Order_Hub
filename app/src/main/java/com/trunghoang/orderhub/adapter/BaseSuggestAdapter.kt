package com.trunghoang.orderhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.trunghoang.orderhub.utils.standardize

abstract class BaseSuggestAdapter<T>(
    context: Context,
    private val itemLayout: Int,
    data: List<T>
) : ArrayAdapter<T>(context, itemLayout, data) {
    var dataAll: MutableList<T> = ArrayList<T>().apply {
        addAll(data)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view = convertView ?: let {
            LayoutInflater.from(context).inflate(
                itemLayout,
                parent,
                false
            )
        }
        getItem(position)?.let {
            setTextItem(view, it)
        }
        return view
    }

    abstract fun setTextItem(view: View, item: T)

    abstract inner class BaseSuggestFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?) =
            FilterResults().apply {
                val suggestions = ArrayList<T>()
                if (constraint.isNullOrBlank()) {
                    suggestions.addAll(dataAll)
                } else {
                    val filterPattern =
                        constraint.toString().standardize()
                    for (item: T in dataAll) {
                        if (checkItem(item, filterPattern)) {
                            suggestions.add(item)
                        }
                    }
                    values = suggestions
                    count = suggestions.size
                }
            }

        override fun publishResults(
            constraint: CharSequence?,
            results: FilterResults?
        ) {
            clear()
            results?.values?.let {
                addAll(it as ArrayList<T>)
                notifyDataSetChanged()
            }
        }

        abstract fun checkItem(item: T, pattern: String): Boolean
    }
}
