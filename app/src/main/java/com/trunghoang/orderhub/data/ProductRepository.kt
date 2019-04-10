package com.trunghoang.orderhub.data

import com.trunghoang.orderhub.data.firebase.ProductFirebaseDataSource
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firebaseDataSource: ProductFirebaseDataSource
) : ProductDataSource {
    override fun getProducts() = firebaseDataSource.getProducts()
}
