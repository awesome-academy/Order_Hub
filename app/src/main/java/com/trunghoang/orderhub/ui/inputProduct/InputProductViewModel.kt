package com.trunghoang.orderhub.ui.inputProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.trunghoang.orderhub.data.ProductRepository
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.Product
import javax.inject.Inject

class InputProductViewModel @Inject constructor(
    productRepository: ProductRepository
) : ViewModel() {
    companion object {
        const val NAME = "InputProductViewModel"
        const val DEFAULT_QUANTITY = 1L
        const val DEFAULT_PRICE = 0L
    }
    val quantity = MutableLiveData<Long>().apply {
        value = DEFAULT_QUANTITY
    }
    val name = MutableLiveData<String>()
    val price = MutableLiveData<Long>().apply {
        value = DEFAULT_PRICE
    }
    val photo = MutableLiveData<String>()
    val product: Product = Product()
        get() {
            field.quantity = quantity.value
            field.price = price.value
            field.name = name.value
            field.photo = photo.value
            return field
        }
    val productsAll: MutableLiveData<APIResponse<List<Product>>> by lazy {
        productRepository.getProducts()
    }

    fun updateProduct(product: Product) {
        name.value = product.name
        price.value = product.price
        photo.value = product.photo
    }
}
