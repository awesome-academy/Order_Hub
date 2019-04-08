package com.trunghoang.orderhub.model

data class Product(
    var name: String? = "",
    var price: Long? = 0,
    var quantity: Long? = 0,
    var totalPrice: Long? = 0,
    var photo: String? = null
)
