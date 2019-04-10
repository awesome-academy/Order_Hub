package com.trunghoang.orderhub.ui.inputProduct

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.trunghoang.orderhub.R
import com.trunghoang.orderhub.adapter.ProductsAdapter
import com.trunghoang.orderhub.databinding.FragmentInputProductBinding
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.EnumStatus
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.utils.toast
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_input_product.*
import javax.inject.Inject
import javax.inject.Named

class InputProductFragment : DialogFragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            InputProductFragment()
    }

    @Inject
    @field:Named(InputProductViewModel.NAME)
    lateinit var viewModel: InputProductViewModel
    private var onProductAddedListener: OnProductAddedListener? = null
    private var productsAdapter: ProductsAdapter? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (parentFragment is OnProductAddedListener) onProductAddedListener =
            parentFragment as OnProductAddedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInputProductBinding.inflate(
            inflater,
            container,
            false
        ).apply {
            viewModel = this@InputProductFragment.viewModel
            lifecycleOwner = this@InputProductFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonConfirm.setOnClickListener {
            onProductAddedListener?.onProductAdded(viewModel.product)
            dismiss()
        }
        viewModel.productsAll.observe(this, Observer {
            consumeProductsAll(it)
        })
        editName.requestFocus()
    }

    private fun consumeProductsAll(productsResponse: APIResponse<List<Product>>) {
        when (productsResponse.status) {
            EnumStatus.LOADING -> {
            }
            EnumStatus.SUCCESS -> {
                setAutoCompProducts(productsResponse)
            }
            EnumStatus.ERROR -> {
                productsResponse.error?.printStackTrace()
                context?.toast(getString(R.string.error_general))
            }
        }
    }

    private fun setAutoCompProducts(productsResponse: APIResponse<List<Product>>) {
        context?.let {
            productsResponse.data?.let { products ->
                productsAdapter = ProductsAdapter(
                    it,
                    R.layout.item_suggest_list,
                    products
                )
                editName.setAdapter(productsAdapter)
                editName.setOnItemClickListener { parent, _, position, _ ->
                    parent.getItemAtPosition(position)?.let {
                        viewModel.updateProduct(it as Product)
                    }
                }
            }
        }
    }

    interface OnProductAddedListener {
        fun onProductAdded(product: Product?)
    }
}
