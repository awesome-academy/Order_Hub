package com.trunghoang.orderhub.ui.orderEditor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.adapter.DistrictsAdapter
import com.trunghoang.orderhub.model.*
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.utils.EventWrapper
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_editor.*
import javax.inject.Inject
import javax.inject.Named

class OrderEditorFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = OrderEditorFragment()
    }

    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var mainViewModel: MainViewModel
    @Inject
    @field:Named(OrderEditorViewModel.NAME)
    lateinit var orderEditorViewModel: OrderEditorViewModel
    private var districtsAdapter: DistrictsAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_order_editor,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addToolbar()
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        mainViewModel.tokenEvent.observe(this, Observer {
            consumeToken(it)
        })
        with(orderEditorViewModel) {
            districtsResponse.observe(this@OrderEditorFragment, Observer {
                consumeDistricts(it)
            })
        }
    }

    private fun consumeToken(tokenEvent: EventWrapper<String>) {
        tokenEvent.peekContent().apply {
            if (isNotBlank()) orderEditorViewModel.getDistricts(
                GHNApiRequest.Districts(this)
            )
        }
    }

    private fun consumeDistricts(districtResponse: APIResponse<List<District>>) {
        when (districtResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                context?.let {
                    districtResponse.data?.let { data ->
                        districtsAdapter = DistrictsAdapter(
                            it,
                            R.layout.item_suggest_list,
                            data
                        )
                        autoCompDistrict.setAdapter(districtsAdapter)
                        autoCompDistrict.setOnItemClickListener { parent, view, position, id ->
                            parent.getItemAtPosition(position)?.let {
                                orderEditorViewModel.district.value =
                                    it as District
                            }
                        }
                    }
                }
            }
            EnumStatus.ERROR -> {
                districtResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun addToolbar() {
        mainViewModel.toolbarInfo.value = ToolbarInfo(
            R.id.toolbarOrderEditor,
            R.drawable.ic_close,
            R.string.order_editor_new_order_title,
            R.menu.menu_editor
        )
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
}
