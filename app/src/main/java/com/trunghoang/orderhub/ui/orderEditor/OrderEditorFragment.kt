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
import com.trunghoang.orderhub.adapter.WardsAdapter
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
    private var wardsAdapter: WardsAdapter? = null

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
            district.observe(this@OrderEditorFragment, Observer {
                consumeDistrict(it)
            })
            serviceId.observe(this@OrderEditorFragment, Observer {
                consumeServiceId(it)
            })
        }
    }

    private fun consumeToken(tokenEvent: EventWrapper<String>) {
        tokenEvent.peekContent().let { token ->
            if (token.isNotBlank()) with (orderEditorViewModel) {
                getDistricts(GHNApiRequest.Districts(token))
                districtsResponse.observe(this@OrderEditorFragment, Observer {
                    consumeDistricts(it)
                })
            }
        }
    }

    private fun consumeDistricts(districtResponse: APIResponse<List<District>>) {
        when (districtResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS ->
                setAutoCompDistrict(districtResponse)
            EnumStatus.ERROR -> {
                districtResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun consumeDistrict(district: District) {
        mainViewModel.tokenEvent.value?.peekContent()?.let { token ->
            with (orderEditorViewModel) {
                getWards(
                    GHNApiRequest.Wards(
                        token,
                        district.id
                    )
                )
                wardsResponse.observe(this@OrderEditorFragment, Observer {
                    consumeWards(it)
                })
                findServices(
                    GHNApiRequest.Services(
                        token,
                        ToDistrictID = district.id
                    )
                )
                servicesResponse.observe(this@OrderEditorFragment, Observer {
                    consumeServices(it)
                })
            }
        }
    }

    private fun consumeWards(wardsResponse: APIResponse<GHNApiResponse.Wards>) {
        when (wardsResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                setAutoCompWards(wardsResponse)
            }
            EnumStatus.ERROR -> {
                wardsResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_loading_wards))
            }
        }
    }

    private fun consumeServices(servicesResponse: APIResponse<List<GHNApiResponse.Service>>) {
        when (servicesResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                orderEditorViewModel.serviceId.value =
                    servicesResponse.data?.find {
                        it.Name == GHNApiResponse.Service.DEFAULT_SERVICE_NAME
                    }?.let {
                        it.ServiceID
                    }
            }
            EnumStatus.ERROR -> {
                servicesResponse.error?.printStackTrace()
                context?.toast(servicesResponse.error.toString())
            }
        }
    }

    private fun consumeServiceId(serviceId: Int) {
        mainViewModel.tokenEvent.value?.peekContent()?.let { token ->
            with (orderEditorViewModel) {
                district.value?.id?.let { districtId ->
                    calculateFee(
                        GHNApiRequest.Fee(
                            token,
                            ToDistrictID = districtId,
                            ServiceID = serviceId
                        )
                    )
                    feeResponse.observe(this@OrderEditorFragment, Observer {
                        consumeFee(it)
                    })
                }
            }
        }
    }

    private fun consumeFee(feeResponse: APIResponse<GHNApiResponse.Fee>) {
        when (feeResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                editShipCost.setText(feeResponse.data?.CalculatedFee.toString())
            }
            EnumStatus.ERROR -> {
                feeResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_loading_fee))
            }
        }
    }

    private fun setAutoCompDistrict(districtResponse: APIResponse<List<District>>) {
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

    private fun setAutoCompWards(wardsResponse: APIResponse<GHNApiResponse.Wards>) {
        context?.let {
            wardsResponse.data?.Wards?.let { data ->
                wardsAdapter = WardsAdapter(
                    it,
                    R.layout.item_suggest_list,
                    data
                )
                autoCompWard.setAdapter(wardsAdapter)
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
