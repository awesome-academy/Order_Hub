package com.trunghoang.orderhub.ui.orderEditor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.adapter.DistrictsAdapter
import com.trunghoang.orderhub.adapter.WardsAdapter
import com.trunghoang.orderhub.databinding.FragmentOrderEditorBinding
import com.trunghoang.orderhub.model.*
import com.trunghoang.orderhub.ui.EditorFragment
import com.trunghoang.orderhub.ui.inputProduct.InputProductFragment
import com.trunghoang.orderhub.ui.mainActivity.MainViewModel
import com.trunghoang.orderhub.utils.EventWrapper
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_order_editor.*
import javax.inject.Inject
import javax.inject.Named

class OrderEditorFragment : Fragment(), EditorFragment,
                            InputProductFragment.OnProductAddedListener {
    companion object {
        private const val ARGUMENT_ID = "ARGUMENT_ID"
        @JvmStatic
        fun newInstance(id: String) = OrderEditorFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_ID, id)
            }
        }
    }

    @Inject
    @field:Named(MainViewModel.NAME)
    lateinit var mainViewModel: MainViewModel
    @Inject
    @field:Named(OrderEditorViewModel.NAME)
    lateinit var orderEditorViewModel: OrderEditorViewModel
    private val orderId: String? by lazy {
        arguments?.getString(ARGUMENT_ID)
    }
    private var districtsAdapter: DistrictsAdapter? = null
    private var wardsAdapter: WardsAdapter? = null
    private var productsAdapter: InputProductAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
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
            lifecycleOwner = this@OrderEditorFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addToolbar()
        buttonAddProduct.setOnClickListener {
            addInputProductFragment()
        }
        with(recyclerProducts) {
            productsAdapter = InputProductAdapter {
                orderEditorViewModel.removeProduct(it)
            }
            adapter = productsAdapter
            layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        }
        mainViewModel.tokenEvent.observe(this, Observer {
            consumeToken(it)
        })
        orderId?.let {
            if (it.isNotBlank()) orderEditorViewModel.getOrder(it)
        }
        with(orderEditorViewModel) {
            orderResponse.observe(this@OrderEditorFragment, Observer {
                consumeOrder(it)
            })
            district.observe(this@OrderEditorFragment, Observer {
                consumeDistrict(it)
            })
            serviceId.observe(this@OrderEditorFragment, Observer {
                consumeServiceId(it)
            })
            products.observe(this@OrderEditorFragment, Observer {
                productsAdapter?.setData(it)
            })
        }
    }

    override fun saveData() {
        orderEditorViewModel.saveOrder()
        orderEditorViewModel.saveOrderResponse.observe(this, Observer {
            consumeSaveOrder(it)
        })
    }

    override fun onProductAdded(product: Product?) {
        orderEditorViewModel.addProduct(product)
    }

    private fun consumeOrder(orderResponse: APIResponse<Order>) {
        when (orderResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                orderEditorViewModel.updateOrder(orderResponse.data)
            }
            EnumStatus.ERROR -> {
                orderResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun consumeToken(tokenEvent: EventWrapper<String>) {
        tokenEvent.peekContent().let { token ->
            if (token.isNotBlank()) {
                orderEditorViewModel.apply {
                    getDistricts(
                        GHNApiRequest.Districts(token)
                    )
                    districtsResponse.observe(
                        this@OrderEditorFragment,
                        Observer {
                            consumeDistricts(it)
                        })
                }
            }
        }
    }

    private fun consumeDistricts(districtResponse: APIResponse<List<District>>) {
        when (districtResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                setAutoCompDistrict(districtResponse)
            }
            EnumStatus.ERROR -> {
                districtResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_load_districts))
            }
        }
    }

    private fun consumeDistrict(district: District) {
        mainViewModel.tokenEvent.value?.peekContent()?.let { token ->
            orderEditorViewModel.apply {
                getWards(
                    GHNApiRequest.Wards(
                        token,
                        district.id
                    )
                )
                wardsResponse.observe(this@OrderEditorFragment, Observer {
                    consumeWards(it)
                })
                district.id?.let { id ->
                    findServices(
                        GHNApiRequest.Services(
                            token,
                            toDistrictID = id
                        )
                    )
                    servicesResponse.observe(
                        this@OrderEditorFragment,
                        Observer {
                            consumeServices(it)
                        })
                }
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
                        it.name == GHNApiResponse.Service.DEFAULT_SERVICE_NAME
                    }?.let {
                        it.serviceID
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
            orderEditorViewModel.district.value?.id?.let { districtId ->
                orderEditorViewModel.apply {
                    calculateFee(
                        GHNApiRequest.Fee(
                            token,
                            toDistrictID = districtId,
                            serviceID = serviceId
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
                editShipCost.setText(feeResponse.data?.calculatedFee.toString())
            }
            EnumStatus.ERROR -> {
                feeResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_loading_fee))
            }
        }
    }

    private fun consumeSaveOrder(saveOrderResponse: APIResponse<Boolean>) {
        when (saveOrderResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                context?.toast(getString(R.string.order_editor_success_saved))
                activity?.onBackPressed()
            }
            EnumStatus.ERROR -> {
                saveOrderResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_general))
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
                        orderEditorViewModel.district.value = it as District
                    }
                }
            }
        }
    }

    private fun setAutoCompWards(wardsResponse: APIResponse<GHNApiResponse.Wards>) {
        context?.let {
            wardsResponse.data?.wards?.let { data ->
                wardsAdapter = WardsAdapter(
                    it,
                    R.layout.item_suggest_list,
                    data
                )
                autoCompWard.setAdapter(wardsAdapter)
                autoCompWard.setOnItemClickListener { parent, view, position, id ->
                    parent.getItemAtPosition(position)?.let {
                        orderEditorViewModel.ward.value = it as Ward
                    }
                }
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
    }

    private fun addInputProductFragment() {
        childFragmentManager.beginTransaction()
            .add(InputProductFragment(), null)
            .commit()
    }
}
