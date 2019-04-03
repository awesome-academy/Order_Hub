package com.trunghoang.orderhub.ui.orderList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.model.OrderStatus
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_list.*
import javax.inject.Inject

class OrderListFragment : Fragment() {
    companion object {
        const val ARGUMENT_STATUS = "status"

        @JvmStatic
        fun newInstance(@OrderStatus status: Int) = OrderListFragment()
            .apply {
                arguments = Bundle().apply {
                    putInt(ARGUMENT_STATUS, status)
                }
            }
    }

    @Inject
    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var orderListViewModel: OrderListViewModel
    @Inject
    lateinit var orderAdapter: OrderAdapter
    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager
    private val status by lazy {
        arguments?.getInt(ARGUMENT_STATUS)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainViewModel.orderStatus.value = status
        orderListViewModel.ordersResponse.observe(this, Observer {
            consumeOrdersResponse(it)
        })
        if (status != null) orderListViewModel.getOrders(status!!)
        with(recyclerOrders) {
            layoutManager = linearLayoutManager
            adapter = orderAdapter
        }
    }

    private fun consumeOrdersResponse(res: APIResponse<List<Order>>) {
        when (res.status) {
            EnumStatus.LOADING -> showProgress(true)
            EnumStatus.SUCCESS -> {
                showProgress(false)
                if (!res.data.isNullOrEmpty()) {
                    showNoResult(false)
                    orderAdapter.orders = res.data!!
                } else {
                    showNoResult(true)
                }
            }
            EnumStatus.ERROR -> {
                showProgress(false)
                showNoResult(true)
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            showNoResult(false)
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showNoResult(show: Boolean) {
        if (show) {
            textNoResult.visibility = View.VISIBLE
        } else {
            textNoResult.visibility = View.GONE
        }
    }
}
