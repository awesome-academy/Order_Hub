package com.trunghoang.orderhub.model

import com.google.firebase.firestore.Exclude

data class Order(
    @Exclude
    var id: String = "",
    var createdTime: Long = 0,
    var lastModified: Long = 0,
    var status: Int = 0,
    var name: String? = "",
    var mobile: String? = "",
    var address: String? = "",
    var products: List<Product>? = null,
    var district: District? = null,
    var ward: Ward? = null,
    var fee: Long? = 0,
    var discount: Long? = 0,
    var cod: Long? = 0
)
