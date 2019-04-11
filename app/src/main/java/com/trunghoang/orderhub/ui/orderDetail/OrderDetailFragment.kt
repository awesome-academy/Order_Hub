package com.trunghoang.orderhub.ui.orderDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.adapter.BaseRecyclerAdapter
import com.trunghoang.orderhub.databinding.FragmentOrderEditorBinding
import com.trunghoang.orderhub.model.EditorEvent
import com.trunghoang.orderhub.model.Order
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.model.ToolbarInfo
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorFragment
import com.trunghoang.orderhub.ui.orderEditor.OrderEditorViewModel
import com.trunghoang.orderhub.utils.EventWrapper
import com.trunghoang.orderhub.utils.hideViewOnScrollUp
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_editor.*
import javax.inject.Inject
import javax.inject.Named

class OrderDetailFragment : OrderEditorFragment() {
    companion object {
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        fun newInstance(id: String) = OrderDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_ID, id)
            }
        }
    }

    @Inject
    @field:Named(OrderEditorViewModel.NAME)
    override lateinit var orderEditorViewModel: OrderEditorViewModel
    override var productsAdapter: BaseRecyclerAdapter<Product>? = null

    @SuppressLint("MissingSuperCall")
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        onAttachSuper(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOrderEditorBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            order = orderEditorViewModel
            lifecycleOwner = this@OrderDetailFragment
        }
        return binding.root
    }

    override fun addToolbar() {
        mainViewModel.toolbarInfo.value = ToolbarInfo(
            R.id.toolbarOrderEditor,
            R.drawable.ic_back,
            R.string.order_editor_detail_title
        )
    }

    override fun initEditModeUI() {
        orderEditorViewModel.editMode.value = false
        orderId?.let { id ->
            if (id.isNotBlank()) {
                buttonEdit?.setOnClickListener { _ ->
                    mainViewModel.orderEditorEvent.value = EventWrapper(
                        EditorEvent(id, true)
                    )
                }
                orderEditorViewModel.getOrder(id)
            }
        }
        addToolbar()
        scrollContainer.hideViewOnScrollUp(buttonEdit)
        productsAdapter = InputProductDetailAdapter {}
    }

    override fun consumeOrderEditMode(order: Order) {
        orderEditorViewModel.updateOrder(order)
    }
}
