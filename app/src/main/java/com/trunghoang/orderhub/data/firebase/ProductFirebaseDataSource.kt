package com.trunghoang.orderhub.data.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.trunghoang.orderhub.data.ProductDataSource
import com.trunghoang.orderhub.model.APIResponse
import com.trunghoang.orderhub.model.Product
import com.trunghoang.orderhub.utils.toLiveData
import io.reactivex.Observable
import javax.inject.Inject

class ProductFirebaseDataSource @Inject constructor(
    private val db: FirebaseFirestore
) : ProductDataSource {
    override fun getProducts() = Observable.create<List<Product>> { emitter ->
        db.collection(FirebaseKeys.COL_PRODUCTS)
            .get()
            .addOnSuccessListener {
                val products = ArrayList<Product>()
                for (snapshot in it.documents) {
                    snapshot.toObject(Product::class.java)?.let { product ->
                        products.add(product)
                    }
                }
                emitter.onNext(products)
            }
            .addOnFailureListener {
                emitter.onError(it)
            }
    }.toLiveData() as MutableLiveData<APIResponse<List<Product>>>
}
