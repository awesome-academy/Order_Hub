package com.trunghoang.orderhub.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T> :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<T>>() {
    private var dataSet = ArrayList<T>()

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(dataSet[position])
    }

    fun setData(dataSet: List<T>) {
        this.dataSet.clear()
        this.dataSet.addAll(dataSet)
        notifyDataSetChanged()
    }

    abstract class BaseViewHolder<T>(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun onBind(item: T)
    }
}
