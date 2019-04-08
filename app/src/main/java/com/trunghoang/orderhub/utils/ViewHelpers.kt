package com.trunghoang.orderhub.utils

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

fun NestedScrollView.hideViewOnScrollUp(view: View?) {
    setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
        if (scrollY > oldScrollY) {
            view?.visibility = View.GONE
        }
        if (scrollY < oldScrollY) {
            view?.visibility = View.VISIBLE
        }
    }
}

fun RecyclerView.hideViewOnScrollUp(view: View?) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                view?.visibility = View.GONE
            } else {
                view?.visibility = View.VISIBLE
            }
        }
    })
}
