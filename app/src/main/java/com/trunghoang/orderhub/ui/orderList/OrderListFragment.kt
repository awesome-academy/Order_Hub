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
import com.trunghoang.orderhub.data.OrderParams
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.OrderStatus
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.ui.mainScreen.MainScreenViewModel
import com.trunghoang.orderhub.utils.EventWrapper
import com.trunghoang.orderhub.utils.getStringIdFromStatus
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_list.*
import javax.inject.Inject
import javax.inject.Named

class OrderListFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = OrderListFragment()
    }

    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var mainViewModel: MainViewModel
    @Inject
    @field:Named(MainScreenViewModel.NAME)
    lateinit var mainScreenViewModel: MainScreenViewModel
    @Inject
    @field:Named(OrderListViewModel.NAME)
    lateinit var orderListViewModel: OrderListViewModel
    @Inject
    lateinit var orderAdapter: OrderAdapter
    private val status by lazy {
        mainScreenViewModel.orderStatusEvent.value?.peekContent()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainScreenViewModel.orderStatusEvent.observe(this, Observer {
            consumeOrderStatus(it)
        })
        orderListViewModel.progressStatus.observe(this, Observer {
            consumeOrderLoadingProgress(it)
        })
        orderListViewModel.ordersPagedList.observe(this, Observer {
            orderAdapter.submitList(it)
        })
        with(recyclerOrders) {
            layoutManager = LinearLayoutManager(this@OrderListFragment.context)
            adapter = orderAdapter
        }
        swipeRefresh.setOnRefreshListener {
            getOrders(status)
            swipeRefresh.isRefreshing = false
        }
        floatNewOrder.setOnClickListener {
            mainViewModel.orderEditorEvent.value = EventWrapper(MainViewModel.NO_STRING)
        }
    }

    private fun consumeOrderLoadingProgress(loadingStatus: EnumStatus) {
        when (loadingStatus) {
            EnumStatus.LOADING -> showProgress(true)
            EnumStatus.SUCCESS -> showProgress(false)
            EnumStatus.ERROR -> {
                showProgress(false)
                showNoResult(true)
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun consumeOrderStatus(orderStatusEvent: EventWrapper<Int>?) {
        orderStatusEvent?.peekContent()?.apply {
            getOrders(this)
            addToolbar(this)
        }
    }

    private fun showProgress(show: Boolean) {
        if (show) {
            showNoResult(false)
            progressBar.visibility = View.VISIBLE
        } else {
            showNoResult(orderAdapter.itemCount == 0)
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

    private fun getOrders(@OrderStatus status: Int?) {
        status?.let {
            orderListViewModel.getOrders(OrderParams<Long>(status = it))
        }
    }

    private fun addToolbar(@OrderStatus status: Int?) {
        status?.let {
            mainViewModel.toolbarInfo.value = ToolbarInfo(
                R.id.toolbarMainScreen,
                R.drawable.ic_menu_black_24dp,
                context?.getStringIdFromStatus(it)
            )
        }
    }
}
